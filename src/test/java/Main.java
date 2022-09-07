public class Main {
    public static void main(String[] args) {
        SwagLabs swagLabs = new SwagLabs();
        try {
            swagLabs.shopping();
        }catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
