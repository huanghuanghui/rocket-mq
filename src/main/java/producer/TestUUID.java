package producer;

import java.util.UUID;

/**
 * @author hhh
 * @date 2019/12/11 14:48
 * @Despriction
 */
public class TestUUID {
  public static void main(String[] args) {
    System.out.println(UUID.randomUUID().toString());

    Boolean a = Boolean.TRUE;
    Boolean b = Boolean.TRUE;
    if (a==b){
      System.out.println(111);
    }
  }
}
