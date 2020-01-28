package consumer;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author hhh
 * @date 2019/12/11 10:27
 * @Despriction Consumer Message
 *
 * 在实际使用场景中，利用何种发送方式，可以总结如下：
 *    当发送的消息不重要时，采用one-way方式，以提高吞吐量；
 *    当发送的消息很重要是，且对响应时间不敏感的时候采用sync方式;
 *    当发送的消息很重要，且对响应时间非常敏感的时候采用async方式；
 */
public class Consumer2 {

  public static void main(String[] args) throws InterruptedException, MQClientException {

    // 用指定的使用者组名称实例化
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

    // 指定名称服务器地址
    consumer.setNamesrvAddr("192.168.1.189:9876");

    // 再订阅一个主题以进行消费
    consumer.subscribe("test1", "*");
    // 注册回调以在从代理获取的消息到达时执行
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                      ConsumeConcurrentlyContext context) {
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(msgs.get(0).getBody()));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });

    //启动使用者实例
    consumer.start();

    System.out.printf("Consumer Started.%n");
  }
}
