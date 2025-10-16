package until;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
    private static final Scanner sc = new Scanner(System.in);
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9,11}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static String readNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("⚠ Không được để trống!");
        }
    }

    public static String readPhone() {
        while (true) {
            System.out.print("Số điện thoại: ");
            String phone = sc.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("⚠ Bắt buộc nhập số điện thoại!");
                continue;
            }
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                System.out.println("⚠ Số điện thoại không hợp lệ (chỉ 9–11 chữ số).");
                continue;
            }
            return phone;
        }
    }

    public static String readEmail() {
        while (true) {
            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("⚠ Bắt buộc nhập email!");
                continue;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("⚠ Email không đúng định dạng!");
                continue;
            }
            return email;
        }
    }

    public static String readYesNo(String message) {
        System.out.print(message + " (Y/N): ");
        return sc.nextLine().trim();
    }

    public static void waitEnter() {
        System.out.print("Nhấn Enter để tiếp tục...");
        sc.nextLine();
    }
}
