package com.today.basic.console;

/**
 * desc: testTryCatch
 *
 * @author hz.lei
 * @since 2018年07月25日 下午2:36
 */
public class TestTryCatch {


    public String testThx() {
        try {
            int i = 1 / 10;
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("finally");
        }
    }

    public static void main(String[] args) {
        TestTryCatch testTryCatch = new TestTryCatch();
        testTryCatch.testThx();
    }


}
