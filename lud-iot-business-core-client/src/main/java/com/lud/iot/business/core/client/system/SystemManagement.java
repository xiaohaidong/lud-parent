/*
 * Copyright 2018-2019 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lud.iot.business.core.client.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.iot.business.core.api.system.entity.CpuEntity;
import com.lud.iot.business.core.api.system.entity.DiskEntity;
import com.lud.iot.business.core.api.system.entity.HardWearEntity;
import com.lud.iot.business.core.api.system.entity.NetWorkEntity;
import com.lud.iot.business.core.api.system.entity.OperatingEntity;
import com.lud.iot.business.core.api.system.entity.SystemEntity;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.value.NumberValue;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;

/**
 * @author sunqinqiu 
 * @date   2019-10-15 03:35
 */
@Service
public class SystemManagement {
    /**
     * 存储当前环境的硬件信息
     * 由于系统的硬件在短时间内不会发生变更，可以用一个静态变量存储，不影响线程安全性
     */
    private SystemEntity system = new SystemEntity();
    
    /**
     * 获取当前系统的硬件信息
     * @author sunqinqiu 
     * @date   2019-10-20 12:02
     * @return
     */
    public SystemEntity getSystem() {
        return system;
    }
    
    /**
     * 获取系统信息
     * @author sunqinqiu 
     * @date   2019-10-15 03:46
     */
    public void resolStaticConfig() {
        SystemInfo sys = new SystemInfo();
        setNetWorkInfo(sys.getHardware().getNetworkIFs());
        getServerip();
        getOperating(sys.getOperatingSystem());
        getHardware(sys);
    }
    
    /**
     * 获取硬件信息
     * @author sunqinqiu 
     * @date   2019-10-16 03:30
     * @param sys
     */
    private void getHardware(SystemInfo sys) {
        HardwareAbstractionLayer hard = sys.getHardware();
        HardWearEntity hardWear = new HardWearEntity();
        hardWear.setBaseboard(ListMapUtil.toMap(JSON.toJSONString(hard.getComputerSystem())));
        Map<String, Serializable> memory = new HashMap<>();
        memory.put("total", hard.getMemory().getTotal() / NumberValue.NUMBER_G);
        memory.put("free", hard.getMemory().getAvailable() / NumberValue.NUMBER_G);
        memory.put("page", hard.getMemory().getPageSize());
        hardWear.setMemory(memory);
        List<DiskEntity> disks = new ArrayList<>();
        for (HWDiskStore store : hard.getDiskStores()) {
            DiskEntity disk = new DiskEntity();
            disk.setName(store.getName().replace("\\", CT.EMPTY));
            disk.setSerial(store.getSerial().replace(CT.SPACE, CT.EMPTY));
            disk.setSize(store.getSize() / NumberValue.NUMBER_G);
            disk.setReadBytes(store.getReadBytes() / NumberValue.NUMBER_M);
            disk.setReads(store.getReads());
            disk.setWriteBytes(store.getWriteBytes() / NumberValue.NUMBER_M);
            disk.setWrites(store.getWrites());
            disk.setTimes(store.getTransferTime());
            disks.add(disk);
        }
        hardWear.setDisks(disks);
        CpuEntity cpu = new CpuEntity();
        CentralProcessor processor = hard.getProcessor();
        cpu.setCpu64bit(processor.isCpu64bit());
        cpu.setFamily(processor.getFamily());
        cpu.setFrequency(processor.getMaxFreq());
        cpu.setId(processor.getProcessorID());
        cpu.setIdentifier(processor.getIdentifier());
        cpu.setLogicalProcessorCount(processor.getLogicalProcessorCount());
        cpu.setModel(processor.getModel());
        cpu.setName(processor.getName());
        cpu.setPhysicalPackageCount(processor.getPhysicalPackageCount());
        cpu.setPhysicalProcessorCount(processor.getPhysicalProcessorCount());
        cpu.setVendor(processor.getVendor());
        hardWear.setCpu(cpu);
        system.setHardWear(hardWear);
    }
    
    /**
     * 获取操作系统的系统
     * @author sunqinqiu 
     * @date   2019-10-20 12:05
     * @param operatingSystem
     */
    private void getOperating(OperatingSystem operatingSystem) {
        OperatingEntity opera = new OperatingEntity();
        opera.setBuildNumber(operatingSystem.getVersion().getBuildNumber());
        opera.setCodeName(operatingSystem.getVersion().getCodeName());
        opera.setVersion(operatingSystem.getVersion().getVersion());
        opera.setDomainName(operatingSystem.getNetworkParams().getDomainName());
        opera.setHostName(operatingSystem.getNetworkParams().getHostName());
        opera.setManufacturer(operatingSystem.getManufacturer());
        opera.setOsname(operatingSystem.getFamily());
        opera.setBitness(operatingSystem.getBitness());
        opera.setElevated(operatingSystem.isElevated());
        system.setOperating(opera);
    }
    
    /**
     * 获取系统绑定的IP地址
     * @author sunqinqiu 
     * @date   2019-10-16 02:24
     * @return
     */
    private void getServerip() {
        long maxSend = -1;
        for (NetWorkEntity net : system.getNetwork()) {
            if (net.getSent() > maxSend && net.getIp().split("\\.").length == 4) {
                maxSend = net.getSent();
                system.setServer(net.getMac().toLowerCase());
            }
        }
    }
    
    /**
     * 设置NetworkInfo
     * @author sunqinqiu 
     * @date   2019-10-16 02:15
     */
    private void setNetWorkInfo(NetworkIF[] list) {
        List<NetWorkEntity> workList = new ArrayList<>();
        for (NetworkIF nif : list) {
            String[] ipv4s = nif.getIPv4addr();
            if (ipv4s.length > 0 && !ipv4s[0].isEmpty()) {
                NetWorkEntity netItem = new NetWorkEntity();
                netItem.setIp(ipv4s[0]);
                netItem.setRecv(nif.getBytesRecv() / NumberValue.NUMBER_M);
                netItem.setRecverr(nif.getInErrors());
                netItem.setRecvps(nif.getPacketsRecv());
                netItem.setSent(nif.getBytesSent() / NumberValue.NUMBER_M);
                netItem.setSenterr(nif.getOutErrors());
                netItem.setSentps(nif.getPacketsSent());
                netItem.setMac(nif.getMacaddr());
                netItem.setName(nif.getName());
                netItem.setTitle(nif.getDisplayName());
                netItem.setMtu(nif.getMTU());
                netItem.setSpeed(nif.getSpeed() / NumberValue.NUMBER_TG);
                workList.add(netItem);
            }
        }
        system.setNetwork(workList);
    }
}
