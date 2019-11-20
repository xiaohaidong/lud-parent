package com.lud.service.open.wxpay;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lud.service.open.api.wxpay.WeChatPayConfig;
import com.lud.service.open.api.wxpay.WeChatPayConfigSystem;
import com.lud.service.open.api.wxpay.WeChatPayReportInfo;
import com.lud.service.open.api.wxpay.WeChatPayReportService;
import com.lud.util.comm.io.NetWorker;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.open.tencent.WeChatPay;
import com.lud.util.content.util.text.CT;

import lombok.Setter;

/**
 * 交易保障
 */
@Service
public class WeChatPayReportServiceImp implements WeChatPayReportService {
    private final Logger                loger          = LoggerFactory.getLogger(this.getClass());
    /**
     * 
     */
    private LinkedBlockingQueue<String> reportMsgQueue = null;
    /**
     * 
     */
    private ExecutorService             executorService;
    
    @Setter
    private boolean                     running        = true;
    
    /**
     * 初始化
     */
    private WeChatPayReportServiceImp() {
        reportMsgQueue = new LinkedBlockingQueue<>(WeChatPayConfigSystem.getReportQueueMaxSize());
        executorService = Executors.newFixedThreadPool(WeChatPayConfigSystem.getReportWorkerNum(), run -> {
            Thread thread = Executors.defaultThreadFactory().newThread(run);
            thread.setDaemon(true);
            return thread;
        });
        if (WeChatPayConfigSystem.isAutoReport()) {
            for (int i = 0; i < WeChatPayConfigSystem.getReportWorkerNum(); ++i) {
                executorService.execute(this::executorService);
            }
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 20:13
     */
    private void executorService() {
        while (running) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                String firstMsg = reportMsgQueue.take();
                String msg = null;
                stringBuilder.append(firstMsg);
                int remainNum = WeChatPayConfigSystem.getReportBatchSize() - 1;
                for (int j = 0; j < remainNum; ++j) {
                    msg = reportMsgQueue.take();
                    if (msg == null) {
                        break;
                    }
                    stringBuilder.append("\n").append(msg);
                }
                NetWorker.httpRequest(WeChatPay.REPORT_URL, stringBuilder.toString(), UserAgentContent.getUserAgent(), WeChatPay.DEFAULT_CONNECT_TIMEOUT_MS, WeChatPay.DEFAULT_READ_TIMEOUT_MS);
            } catch (Exception ex) {
                loger.warn("report fail. reason: {}", ex.getMessage());
            }
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 21:15
     * @param reportInfo
     * @param key
     * @return
     */
    private String getWeChatPayReportInfoString(final WeChatPayReportInfo reportInfo, String key) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Object[] objects = new Object[] { reportInfo.getVersion(), reportInfo.getSdk(), reportInfo.getUuid(), reportInfo.getTimestamp(), reportInfo.getElapsedTimeMillis(), reportInfo.getFirstDomain(), reportInfo.isPrimaryDomain(), reportInfo.getFirstConnectTimeoutMillis(), reportInfo.getFirstReadTimeoutMillis(), reportInfo.getFirstHasDnsError(), reportInfo.getFirstHasConnectTimeout(), reportInfo.getFirstHasReadTimeout() };
            for (Object object : objects) {
                stringBuilder.append(object).append(",");
            }
            String sign = DEncrypt.getMacSha256(stringBuilder.toString(), key);
            stringBuilder.append(sign);
            return stringBuilder.toString();
        } catch (Exception ex) {
            return CT.EMPTY;
        }
    }
    
    /**
     * 获取report
     * @author sunqinqiu 
     * @date   2019-09-14 21:20
     * @param uuid
     * @param elapsedTimeMillis
     * @param firstDomain
     * @param primaryDomain
     * @param firstConnectTimeoutMillis
     * @param firstReadTimeoutMillis
     * @param firstHasDnsError
     * @param firstHasConnectTimeout
     * @param firstHasReadTimeout
     * @return 
     */
    @Override
    public boolean report(final WeChatPayConfig config, String uuid, long elapsedTimeMillis, String firstDomain, boolean primaryDomain, int firstConnectTimeoutMillis, int firstReadTimeoutMillis, boolean firstHasDnsError, boolean firstHasConnectTimeout, boolean firstHasReadTimeout) {
        long currentTimestamp = DateUtil.getCurrentTimestamp();
        WeChatPayReportInfo reportInfo = new WeChatPayReportInfo(uuid, currentTimestamp, elapsedTimeMillis, firstDomain, primaryDomain, firstConnectTimeoutMillis, firstReadTimeoutMillis, firstHasDnsError, firstHasConnectTimeout, firstHasReadTimeout);
        String data = getWeChatPayReportInfoString(reportInfo, config.getKey());
        if (data != null) { return reportMsgQueue.offer(data); }
        return false;
    }
}
