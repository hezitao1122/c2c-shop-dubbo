package com.zeryts.c2c.social.govern.report;

import com.zeryts.c2c.social.govern.report.config.db.DruidDataSourceConfig;
import org.apache.ibatis.transaction.Transaction;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

@SpringBootApplication
@Import(DruidDataSourceConfig.class)
public class SocialGovernReportApplication {

    public static void main(String[] args) {

        SpringApplication.run(SocialGovernReportApplication.class, args);
    }


    public static void sendMessage() throws MQClientException, UnsupportedEncodingException {
        /*
            ����RocketMQ�ص���һ���������ӿ�
            �����ִ�б�������,commit \ rollback , �ص��������߼�
         */
        TransactionListener transactionListener = new TransactionListenerImpl();

        // ����һ��֧��������Ϣ��Producer
        // ����ָ��һ�������߷���
        TransactionMQProducer producer = new TransactionMQProducer("test_producer_group");

        //ָ��һ���̳߳�,�������һЩ�߳�
        // ����̳߳�����������RocketMQ�ص��������
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("TestThread");
                        return t;
                    }
                }
        );

        // ����������������Ӧ���̳߳�
        producer.setExecutorService(executorService);
        // �������������ü���
        producer.setTransactionListener(transactionListener);
        producer.start();

        Message message = new Message(
                "PayOrderSucessTopic",
                "TestTag",
                "TestKey",
                ("����֧����Ϣ").getBytes(RemotingHelper.DEFAULT_CHARSET)

        );

        try {
            //��������Ϣ���ͳ�ȥ
            TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(message, null);

        } catch (Exception e) {
            // �������half��Ϣ����ʧ��
            // ���ݽ��лص�
        }

    }
}

class TransactionListenerImpl implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {


            // ������ִ�ж����ı�������
            // ���ݽ��ȥѡ��commit����rollback
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            // �����������ִ��ʧ��,�ع�һ��
            // ����Ϣ����Ϊrollback
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * �����Ϊ����ԭ��,û�з���commit��rollback
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        // ��ѯ���������Ƿ�ִ�гɹ���

        // ���ݱ��������ִ�����ѡ��commit��rollback

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
