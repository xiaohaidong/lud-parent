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
package com.lud.util.comm.runtime;

import java.io.Serializable;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListComparator;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.JVMText;
import com.lud.util.content.util.value.NumberValue;

/**
 * 对JVM数据的一些统计和分析
 * @author sunqinqiu 
 * @date   2018-05-26 03:18
 */
public final class JVMInfo {
    
    /**
     * 系统级别的数据分析
     */
    private static final OperatingSystemMXBean SYSTEM      = ManagementFactory.getOperatingSystemMXBean();
    private static final CompilationMXBean     COMPILATION = ManagementFactory.getCompilationMXBean();
    private static final ClassLoadingMXBean    CLASSLOAD   = ManagementFactory.getClassLoadingMXBean();
    private static final RuntimeMXBean         RUNTIME     = ManagementFactory.getRuntimeMXBean();
    
    /**
     * 获取运行环境
     * @author sunqinqiu 
     * @date   2018-05-26 10:49
     * @return
     */
    public static Map<String, Serializable> getRuntimeInfo() {
        Map<String, Serializable> map = new LinkedHashMap<>();
        map.put(JVMText.RUNTIME_NAME, RUNTIME.getName());
        map.put(JVMText.RUNTIME_SN, RUNTIME.getSpecName());
        map.put(JVMText.RUNTIME_SV, RUNTIME.getSpecVendor());
        map.put(JVMText.RUNTIME_SVS, RUNTIME.getSpecVersion());
        map.put(JVMText.RUNTIME_ST, new Date(RUNTIME.getStartTime()));
        map.put(JVMText.RUNTIME_UT, new Date(RUNTIME.getUptime()));
        map.put(JVMText.RUNTIME_VN, RUNTIME.getVmName());
        map.put(JVMText.RUNTIME_VV, RUNTIME.getVmVendor());
        map.put(JVMText.RUNTIME_VVS, RUNTIME.getVmVersion());
        map.put("class", (Serializable) getClassInfo());
        map.put("gc", (Serializable) getGCInfo());
        map.put("mmi", (Serializable) getMemoryManagerInfo());
        map.put("mmr", (Serializable) getMMRInfo());
        map.put("mmrpool", (Serializable) getMMRPoolInfo());
        map.put("thread", (Serializable) getThreadInfo());
        return map;
    }
    
    /**
     * 获取JVM加载的类的信息
     * @author sunqinqiu 
     * @date   2018-05-26 10:45
     * @return
     */
    private static Map<String, Serializable> getClassInfo() {
        Map<String, Serializable> map = new LinkedHashMap<>();
        map.put(JVMText.CLASS_COUNT, CLASSLOAD.getTotalLoadedClassCount());
        map.put(JVMText.CLASS_LOADED, CLASSLOAD.getLoadedClassCount());
        map.put(JVMText.CLASS_UNLOADED, CLASSLOAD.getUnloadedClassCount());
        return map;
    }
    
    /**
     * 获取垃圾回收信息
     * @author sunqinqiu 
     * @date   2018-05-26 11:14
     * @return
     */
    
    private static List<Map<String, Serializable>> getGCInfo() {
        List<Map<String, Serializable>> list = new ArrayList<>();
        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbage : garbages) {
            Map<String, Serializable> item = new HashMap<>();
            item.put(JVMText.GC_NAME, garbage.getName());
            item.put(JVMText.GC_COUNT, garbage.getCollectionCount());
            item.put(JVMText.GC_TIME, garbage.getCollectionTime());
            item.put(JVMText.GC_POOL_NAMES, Arrays.deepToString(garbage.getMemoryPoolNames()));
            list.add(item);
        }
        return list;
    }
    
    /**
     * 获取SYSTEM的一些私有信息
     * @author sunqinqiu 
     * @date   2018-05-26 03:48
     * @param operatingSystem
     * @param methodName
     * @return
     */
    private static long getLongFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
        try {
            final Method method = operatingSystem.getClass().getMethod(methodName, (Class<?>[]) null);
            method.setAccessible(true);
            return (Long) method.invoke(operatingSystem, (Object[]) null);
        } catch (Exception ex) {
            return 0;
        }
    }
    
    /**
     * 获取VM内存管理
     * @author sunqinqiu 
     * @date   2018-05-26 10:58
     * @return
     */
    
    private static List<Map<String, Serializable>> getMemoryManagerInfo() {
        List<Map<String, Serializable>> list = new ArrayList<>();
        List<MemoryManagerMXBean> managers = ManagementFactory.getMemoryManagerMXBeans();
        if (managers != null && !managers.isEmpty()) {
            managers.forEach(manager -> {
                Map<String, Serializable> item = new HashMap<>();
                item.put(CT.STRING_NAME, manager.getName());
                item.put(JVMText.MP_NAMES, Arrays.deepToString(manager.getMemoryPoolNames()));
                ObjectName onjName = manager.getObjectName();
                item.put(CT.STRING_DOMAIN, onjName.getDomain());
                item.put(JVMText.CAN_NAME, onjName.getCanonicalName());
                list.add(item);
            });
        }
        return list;
    }
    
    /**
     * 获取内存信息
     * @author sunqinqiu 
     * @date   2018-05-26 11:23
     * @return
     */
    private static Map<String, Serializable> getMMRInfo() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();
        MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();
        Map<String, Serializable> map = new LinkedHashMap<>();
        
        map.put(JVMText.MMR_HEAD_INIT, headMemory.getInit() / NumberValue.NUMBER_M);
        map.put(JVMText.MMR_HEAD_MAX, headMemory.getMax() / NumberValue.NUMBER_M);
        map.put(JVMText.MMR_HEAD_USED, headMemory.getUsed() / NumberValue.NUMBER_M);
        map.put(JVMText.MMR_HEAD_CMT, headMemory.getCommitted() / NumberValue.NUMBER_M);
        map.put(JVMText.MMR_NOHEAD_INIT, nonheadMemory.getInit() / NumberValue.NUMBER_M);
        map.put(JVMText.MMR_NOHEAD_MAX, nonheadMemory.getMax() / NumberValue.NUMBER_M);
        map.put(JVMText.MMR_NOHEAD_USED, nonheadMemory.getUsed() / NumberValue.NUMBER_M);
        map.put(JVMText.MMR_NOHEAD_CMT, nonheadMemory.getCommitted() / NumberValue.NUMBER_M);
        
        long totalPhysicalMemory = getLongFromOperatingSystem(SYSTEM, JVMText.GTP_M_S);
        long freePhysicalMemory = getLongFromOperatingSystem(SYSTEM, JVMText.GFP_M_S);
        long usedPhysicalMemorySize = totalPhysicalMemory - freePhysicalMemory;
        map.put(JVMText.SYS_M_T, totalPhysicalMemory / NumberValue.NUMBER_M);
        map.put(JVMText.SYS_M_F, freePhysicalMemory / NumberValue.NUMBER_M);
        map.put(JVMText.SYS_M_U, usedPhysicalMemorySize / NumberValue.NUMBER_M);
        long totalSwapSpaceSize = getLongFromOperatingSystem(SYSTEM, JVMText.GTS_S_S);
        long freeSwapSpaceSize = getLongFromOperatingSystem(SYSTEM, JVMText.GFS_S_S);
        long usedSwapSpaceSize = totalSwapSpaceSize - freeSwapSpaceSize;
        map.put(JVMText.SYS_S_T, totalSwapSpaceSize / NumberValue.NUMBER_M);
        map.put(JVMText.SYS_S_F, freeSwapSpaceSize / NumberValue.NUMBER_M);
        map.put(JVMText.SYS_S_U, usedSwapSpaceSize / NumberValue.NUMBER_M);
        return map;
    }
    
    /**
     * 获取MMRPOOL信息
     * @author sunqinqiu 
     * @date   2018-05-26 11:30
     * @return
     */
    private static List<Map<String, Serializable>> getMMRPoolInfo() {
        List<Map<String, Serializable>> list = new ArrayList<>();
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        if (pools != null && !pools.isEmpty()) {
            pools.forEach(pool -> {
                Map<String, Serializable> item = new HashMap<>();
                item.put(CT.STRING_NAME, pool.getName());
                item.put(JVMText.MMR_INIT, +pool.getUsage().getInit() / NumberValue.NUMBER_M);
                item.put(JVMText.MMR_MAX, pool.getUsage().getMax() / NumberValue.NUMBER_M);
                item.put(JVMText.MMR_USED, pool.getUsage().getUsed() / NumberValue.NUMBER_M);
                item.put(JVMText.MMR_CMT, pool.getUsage().getCommitted() / NumberValue.NUMBER_M);
                item.put(JVMText.MMR_PRI, pool.getUsage().getUsed() * 100 / pool.getUsage().getCommitted() + CharacterContent.STRING_PERSONT);
                list.add(item);
            });
        }
        return list;
    }
    
    /**
     * 获取系统级数据
     * @author sunqinqiu 
     * @date   2018-05-26 03:48
     * @return
     */
    public static Map<String, Serializable> getSystemInfo() {
        Map<String, Serializable> map = new LinkedHashMap<>();
        map.put(JVMText.SYS_NAME, SYSTEM.getName());
        map.put(JVMText.SYS_VERSION, SYSTEM.getVersion());
        map.put(JVMText.SYS_ARCH, SYSTEM.getArch());
        map.put(JVMText.SYS_PROC, SYSTEM.getAvailableProcessors());
        map.put(JVMText.SYS_JITNAME, COMPILATION.getName());
        if (COMPILATION.isCompilationTimeMonitoringSupported()) {
            map.put(JVMText.SYS_JITCOM, COMPILATION.getTotalCompilationTime() + " ms");
        }
        System.getProperties().forEach((key, value) -> {
            if (Convert.toString(value).length() < 200) {
                map.put(key.toString(), Convert.toString(value));
            }
        });
        return map;
    }
    
    /**
     * 获取线程信息
     * @author sunqinqiu 
     * @date   2018-05-26 11:43
     * @return
     */
    private static Map<String, Serializable> getThreadInfo() {
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        Map<String, Serializable> info = new HashMap<>();
        info.put(CT.STRING_COUNT, thread.getThreadCount());
        info.put(JVMText.COUNT_PEAK, thread.getPeakThreadCount());
        info.put(JVMText.COUNT_TOTAL, thread.getTotalStartedThreadCount());
        info.put(JVMText.COUNT_DAEMON, thread.getDaemonThreadCount());
        return info;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-18 00:26
     * @param threadInfos
     * @param isdead
     * @return
     */
    private static List<Map<String, Serializable>> getThreadInfo(ThreadInfo[] threadInfos, String isdead) {
        List<Map<String, Serializable>> list = new ArrayList<>();
        for (ThreadInfo threadInfo : threadInfos) {
            Map<String, Serializable> info = new HashMap<>();
            info.put(CT.STRING_ID, threadInfo.getThreadId());
            info.put(CT.STRING_NAME, threadInfo.getThreadName());
            info.put(CT.STRING_STATE, threadInfo.getThreadState());
            info.put(JVMText.BLOCKED_COUNT, threadInfo.getBlockedCount());
            info.put(JVMText.WAITED_TIME, threadInfo.getWaitedTime());
            info.put(JVMText.LOCK_OWNER_NAME, threadInfo.getLockOwnerName());
            info.put(JVMText.IS_NATIVE, threadInfo.isInNative());
            info.put(JVMText.IS_DEAD, isdead);
            list.add(info);
        }
        return list;
    }
    
    /**
     * 获取线程数据
     * @author sunqinqiu 
     * @date   2018-05-26 11:50
     * @return
     */
    public static List<Map<String, Serializable>> getThreadsInfo() {
        List<Map<String, Serializable>> list = new ArrayList<>();
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        long[] threadIds = thread.getAllThreadIds();
        if (threadIds != null && threadIds.length > 0) {
            ThreadInfo[] threadInfos = thread.getThreadInfo(threadIds);
            list.addAll(getThreadInfo(threadInfos, CT.STRING_NO));
        }
        long[] deadlockedIds = thread.findDeadlockedThreads();
        if (deadlockedIds != null && deadlockedIds.length > 0) {
            ThreadInfo[] deadThreadInfos = thread.getThreadInfo(deadlockedIds);
            list.addAll(getThreadInfo(deadThreadInfos, CT.STRING_YES));
        }
        list.sort(new ListComparator(CT.STRING_ID));
        return list;
    }
    
    private JVMInfo() {}
}
