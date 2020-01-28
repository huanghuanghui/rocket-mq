package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author hhh
 * @date 2019/12/11 10:25
 * @Despriction Reliable synchronous transmission is used in extensive scenes, such as important notification messages, SMS notification, SMS marketing system, etc.
 * 同步数据传输
 *      发送消息采用同步模式，这种方式只有在消息完全发送完成之后才返回结果，此方式存在需要同步等待发送结果的时间代价。
 *      这种方式具有内部重试机制，即在主动声明本次消息发送失败之前，内部实现将重试一定次数，默认为2次
 *     （DefaultMQProducer＃getRetryTimesWhenSendFailed）。 发送的结果存在同一个消息可能被多次发送给给broker，这里需要应用的开发者自己在消费端处理幂等性问题。
 */
public class SyncProducer {
  public static void main(String[] args) throws Exception {
    //用生产者组名称实例化
    DefaultMQProducer producer = new DefaultMQProducer("HHH_GROUP");
    // 指定名称服务器地址
    producer.setNamesrvAddr("192.168.1.189:9876");
    //Launch the instance.
    producer.start();
    for (int i = 0; i < 100; i++) {
      //创建一个消息实例，指定主题，标签和消息正文
      Message msg = new Message("test1" /* Topic */, "TagA" /* Tag */, ("同ba步传输 " + i + "条信息").
        getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
      );
      //Call send message to deliver message to one of brokers.
      SendResult sendResult = producer.send(msg);
      System.out.printf("%s%n", sendResult);
    }
    //Shut down once the producer instance is not longer in use.
    producer.shutdown();
  }
}
