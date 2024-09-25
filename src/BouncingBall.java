import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BouncingBall extends JPanel implements ActionListener {

    private int x = 150, y = 150;   // Початкові координати кола
    private int radius = 20;        // Радіус кола
    private int dx = 2, dy = 3;     // Швидкість зміщення по осям
    private boolean isCompressed = false; // Флаг для перевірки стиснення
    private Timer timer;

    public BouncingBall() {
        // Налаштовуємо таймер для оновлення положення кола кожні 10 мс
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Малюємо коло
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Перевірка на зіткнення з границями фрейму
        if (x - radius < 0 || x + radius > getWidth()) {
            dx = -dx; // Міняємо напрямок руху по X
            isCompressed = true; // Активуємо ефект стиснення
        }

        if (y - radius < 0 || y + radius > getHeight()) {
            dy = -dy; // Міняємо напрямок руху по Y
            isCompressed = true; // Активуємо ефект стиснення
        }

        // Оновлюємо координати
        x += dx;
        y += dy;

        // Ефект стиснення: зменшення радіуса тільки при зіткненні
        if (isCompressed) {
            radius = Math.max(15, (int) (radius * 0.95)); // Мінімальний радіус - 15
            if (radius == 15) {
                isCompressed = false; // Повертаємо до нормального розміру після стиснення
            }
        } else {
            radius = Math.min(20, radius + 1); // Поступове відновлення до нормального розміру
        }

        // Перемальовуємо панель
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Пружний рух кола");
        BouncingBall ball = new BouncingBall();

        frame.add(ball);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
