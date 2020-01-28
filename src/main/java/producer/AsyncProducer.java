package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author hhh
 * @date 2019/12/11 11:07
 * @Despriction 异步传输通常用于对时间敏感的业务场景中
 *      发送消息采用异步发送模式，消息发送后立刻返回，当消息完全完成发送后，会调用回调函数sendCallback来告知发送者本次发送是成功或者失败。
 *      异步模式通常用于响应时间敏感业务场景，即承受不了同步发送消息时等待返回的耗时代价。
 *      同同步发送一样，异步模式也在内部实现了重试机制，默认次数为2次（DefaultMQProducer#getRetryTimesWhenSendAsyncFailed}）。
 *      发送的结果同样存在同一个消息可能被多次发送给给broker，需要应用的开发者自己在消费端处理幂等性问题。
 */
public class AsyncProducer {
  public static void main(String[] args) throws Exception {
    //用生产者组名称实例化
    DefaultMQProducer producer = new DefaultMQProducer("HHH_GROUP");
    // 指定名称服务器地址
    producer.setNamesrvAddr("192.168.1.189:9876");
    //启动实例
    producer.start();
    producer.setRetryTimesWhenSendAsyncFailed(0);
    for (int i = 0; i < 100; i++) {
      final int index = i;
      //Create a message instance, specifying topic, tag and message body.
      Message msg = new Message("test1", "TagA", "OrderID188",
        ("异步传输"+i+"条消息").getBytes(RemotingHelper.DEFAULT_CHARSET));
      producer.send(msg, new SendCallback() {
        public void onSuccess(SendResult sendResult) {
          System.out.printf("%-10d OK %s %n", index,
            sendResult.getMsgId());
        }
        public void onException(Throwable e) {
          System.out.printf("%-10d Exception %s %n", index, e);
          e.printStackTrace();
        }
      });
    }
    //Shut down once the producer instance is not longer in use.
    producer.shutdown();
  }
}
