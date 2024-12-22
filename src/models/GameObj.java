package models;

import java.util.ArrayList;
import java.util.Random;

public class GameObj {

    public int x; // إحداثيات السلحفاة على المحور السيني
    public int y; // إحداثيات السلحفاة على المحور الصادي
    public String type; // نوع الكائن (سلحفاة هنا)
    public boolean remove; // علامة للإزالة عند الاصطدام
    public int textureIndex; // مؤشر الصورة المستخدمة
    private static ArrayList<GameObj> gameObjects = new ArrayList<>(); // قائمة الكائنات
    private static int score = 0; // النقاط
    private static Random random = new Random(); // لتحديد مكان ظهور السلحفاة بشكل عشوائي

    // منشئ GameObj
    public GameObj(int x, int y, String type, int textureIndex) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.remove = false;
        this.textureIndex = textureIndex;
    }

    // إضافة سلحفاة جديدة
    public static void spawnTurtle(int textureIndex) {
        int startX = random.nextBoolean() ? 0 : 100; // تحديد موقع البداية (يمين أو يسار)
        int startY = 15; // الارتفاع الابتدائي
        GameObj turtle = new GameObj(startX, startY, "Turtle", textureIndex);
        gameObjects.add(turtle);
    }

    // تحديث حركة السلاحف
    public static void updateTurtles() {
        for (GameObj turtle : gameObjects) {
            if (turtle.type.equals("Turtle")) {
                // تحريك السلحفاة
                if (turtle.x < 50) {
                    turtle.x += 1; // تتحرك من اليسار إلى اليمين
                } else {
                    turtle.x -= 1; // تتحرك من اليمين إلى اليسار
                }
            }
        }
    }

    // التحقق من الاصطدام مع اللاعب
    public static void checkCollision(int playerX, int playerY) {
        ArrayList<GameObj> toRemove = new ArrayList<>();
        for (GameObj turtle : gameObjects) {
            // إذا كانت السلحفاة قريبة من اللاعب
            if (Math.abs(turtle.x - playerX) < 5 && Math.abs(turtle.y - playerY) < 5) {
                toRemove.add(turtle); // إضافة السلحفاة إلى قائمة الإزالة
                score += 10; // زيادة النقاط
            }
        }
        // إزالة السلاحف المصطدمة
        gameObjects.removeAll(toRemove);
    }

    // الحصول على النقاط الحالية
    public static int getScore() {
        return score;
    }

    // للحصول على جميع الكائنات
    public static ArrayList<GameObj> getGameObjects() {
        return gameObjects;
    }
}
