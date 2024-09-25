import javax.swing.*; // Імпортуємо бібліотеки для створення графічного інтерфейсу
import java.awt.*; // Імпортуємо бібліотеки для роботи з графікою та компонетами
import java.awt.event.ActionEvent; // Імпортуємо клас для обробки подій кнопок
import java.awt.event.ActionListener; // Імпортуємо інтерфейс для слухача подій
import java.io.File; // Імпортуємо клас для роботи з файлами
import java.io.FileNotFoundException; // Імпортуємо клас для обробки виключень при відкритті файлів
import java.util.Scanner; // Імпортуємо клас для зчитування вхідних даних

// Власне виключення, що наслідується від ArithmeticException
class CustomArithmeticException extends ArithmeticException {
    // Конструктор класу для передачі повідомлення
    public CustomArithmeticException(String message) {
        super(message);
    }
}

// Головний клас програми, що розширює JFrame для створення вікна
public class Task3GUI extends JFrame {
    private JTextField filePathField; // Поле для введення шляху до файлу
    private JButton loadButton; // Кнопка для завантаження даних
    private JLabel resultLabel; // Мітка для відображення результатів
    private JTable resultTable; // Таблиця для відображення результатів
    private int[][] matrixA; // Перша матриця
    private int[][] matrixB; // Друга матриця

    // Конструктор для ініціалізації GUI
    public Task3GUI() {
        setTitle("Matrix Calculation"); // Заголовок вікна
        setSize(400, 400); // Розміри вікна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Закриття програми при закритті вікна
        setLocationRelativeTo(null); // Встановлення вікна по центру екрану

        JPanel panel = new JPanel(); // Створення панелі для компонетів
        panel.setLayout(new BorderLayout()); // Встановлення менеджера макету

        // Поле для введення шляху до файлу
        filePathField = new JTextField(20); // Поле вводу з максимальною шириною 20
        loadButton = new JButton("Load Data"); // Створення кнопки

        // Мітка для результатів
        resultLabel = new JLabel("Result will be displayed here."); // Ініціалізація мітки

        // Панель для введення даних
        JPanel inputPanel = new JPanel(); // Створення панелі для вводу
        inputPanel.add(new JLabel("File path:")); // Додавання тексту
        inputPanel.add(filePathField); // Додавання поля для вводу
        inputPanel.add(loadButton); // Додавання кнопки

        panel.add(inputPanel, BorderLayout.NORTH); // Додавання панелі вводу в верхню частину
        panel.add(resultLabel, BorderLayout.SOUTH); // Додавання мітки з результатами в нижню частину

        // Додавання таблиці для відображення результатів
        resultTable = new JTable(); // Створення таблиці
        JScrollPane scrollPane = new JScrollPane(resultTable); // Додавання прокрутки
        panel.add(scrollPane, BorderLayout.CENTER); // Додавання таблиці в центр панелі

        add(panel); // Додавання основної панелі в вікно

        // Обробник кнопки "Load Data"
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Метод, що викликається при натисканні кнопки
                String filePath = filePathField.getText(); // Отримання шляху з поля вводу
                try {
                    loadDataFromFile(filePath); // Спроба завантаження даних з файлу
                    int[] result = calculateX(matrixA, matrixB, matrixA.length); // Обчислення результату
                    showResult(result); // Відображення результату
                } catch (FileNotFoundException ex) {
                    // Виведення повідомлення про помилку, якщо файл не знайдено
                    JOptionPane.showMessageDialog(null, "File not found: " + filePath);
                } catch (NumberFormatException ex) {
                    // Виведення повідомлення про помилку, якщо формат даних невірний
                    JOptionPane.showMessageDialog(null, "Invalid data format in the file.");
                } catch (CustomArithmeticException ex) {
                    // Виведення повідомлення про власне виключення
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    // Метод для завантаження даних з файлу
    private void loadDataFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath); // Створення об'єкта файлу
        Scanner scanner = new Scanner(file); // Ініціалізація сканера для читання файлу

        int n = Integer.parseInt(scanner.nextLine()); // Зчитування розміру матриці
        matrixA = new int[n][n]; // Ініціалізація матриці A
        matrixB = new int[n][n]; // Ініціалізація матриці B

        // Заповнення матриць
        fillMatrix(matrixA, n, scanner); // Заповнення матриці A
        fillMatrix(matrixB, n, scanner); // Заповнення матриці B

        scanner.close(); // Закриття сканера
    }

    // Метод для заповнення матриці
    private void fillMatrix(int[][] matrix, int n, Scanner scanner) throws NumberFormatException {
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" "); // Зчитування рядка та розділення за пробілом
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(line[j]); // Перетворення рядка в ціле число та заповнення матриці
            }
        }
    }

    // Метод для обчислення результату
    public static int[] calculateX(int[][] A, int[][] B, int n) {
        int[] X = new int[n]; // Масив для результату
    
        for (int i = 0; i < n; i++) {
            boolean allNegative = true; // Вважаємо, що всі елементи від'ємні
            for (int j = 0; j < n; j++) {
                // Виведення значень для відлагодження
                System.out.println("A[" + i + "][" + j + "] = " + A[i][j] + ", B[" + i + "][" + j + "] = " + B[i][j]);
                if (A[i][j] >= 0 || B[i][j] >= 0) { // Перевірка на невід'ємні елементи
                    allNegative = false; // Якщо знайдено невід'ємний, змінюємо прапорець
                    break; // Вихід з циклу
                }
            }
            // Присвоєння результату залежно від умови
            if (allNegative) {
                X[i] = 1; // Якщо всі елементи від'ємні, присвоюємо 1
            } else {
                X[i] = 0; // Інакше присвоюємо 0
            }
            // Виведення результату для рядка
            System.out.println("Рядок " + i + " має значення X[" + i + "] = " + X[i]);
        }
        return X; // Повертаємо масив результату
    }
    
    // Метод для відображення результату у таблиці
    private void showResult(int[] result) {
        String[] columnNames = {"Result"}; // Назви колонок
        Object[][] data = new Object[result.length][1]; // Ініціалізація масиву для даних

        for (int i = 0; i < result.length; i++) {
            data[i][0] = result[i]; // Заповнення масиву результатами
        }

        // Встановлення моделі таблиці
        resultTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        resultLabel.setText("Calculation complete."); // Оновлення мітки з результатом
    }

    // Головний метод програми
    public static void main(String[] args) {
        // Запуск GUI в окремому потоці
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Task3GUI().setVisible(true); // Відображення GUI
            }
        });
    }
}
