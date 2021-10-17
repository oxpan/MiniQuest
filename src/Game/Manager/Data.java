package Game.Manager;

public class Data {
    public static long time;

    public static void setTime(long l) { time = l; }
    public static long getTime() { return time; }

    public static boolean isDead;

    public static void setIsDead(boolean is){ isDead = is;}
    public static boolean getIsDead(){return isDead;}

    public static boolean isWather;

    public static void setIsWather(boolean is){
        isWather = is;
    }
    public static boolean getWather(){
        return isWather;
    }
}
