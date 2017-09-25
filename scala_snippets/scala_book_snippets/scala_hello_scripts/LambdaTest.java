public class LambdaTest{

    public static void main(String[] args){
        actionIO(i -> System.out.println(i), s -> System.out.println(s));
    }

    public static void actionIO(IntLambdaIO intLambdaIO, StringLambdaIO stringLambdaIO){
        intLambdaIO.actionIO(100);
        stringLambdaIO.actionIO("Hello world");
    }

    interface IntLambdaIO{
        void actionIO(int i);
    }

    interface StringLambdaIO{
        void actionIO(String s);
    }
}