package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author hhh
 * @date 2019/12/11 11:12
 * @Despriction 单向传输用于要求中等可靠性的情况，例如日志收集。
 *    采用one-way发送模式发送消息的时候，发送端发送完消息后会立即返回，不会等待来自broker的ack来告知本次消息发送是否完全完成发送。
 *    这种方式吞吐量很大，但是存在消息丢失的风险，所以其适用于不重要的消息发送，比如日志收集。
 */
public class OnewayProducer {
  public static void main(String[] args) throws Exception{
    //Instantiate with a producer group name.
    DefaultMQProducer producer = new DefaultMQProducer("HHH_GROUP");
    // Specify name server addresses.
    producer.setNamesrvAddr("192.168.1.189:9876");
    //Launch the instance.
    producer.start();
    for (int i = 0; i < 100; i++) {
      //Create a message instance, specifying topic, tag and message body.
      Message msg = new Message("test1" /* Topic */,
        "TagA" /* Tag */,
        ("单向传输 " + i +"条消息").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
      );
      msg.setTags("TestTags");
      //Call send message to deliver message to one of brokers.
      producer.sendOneway(msg);

    }
    //Shut down once the producer instance is not longer in use.
    producer.shutdown();
  }
}
