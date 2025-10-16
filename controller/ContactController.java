package controller;

import model.Contact;
import service.ContactService;
import until.Validation;
import java.util.List;
import java.util.Scanner;

public class ContactController {
    private final ContactService service = new ContactService();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n----- CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ -----");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Đọc từ file");
            System.out.println("7. Lưu vào file");
            System.out.println("8. Thoát");

            System.out.print("Chọn chức năng: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> service.showContacts();
                case "2" -> addContact();
                case "3" -> updateContact();
                case "4" -> deleteContact();
                case "5" -> searchContact();
                case "6" -> confirmAndRead();
                case "7" -> confirmAndWrite();
                case "8" -> { System.out.println("Tạm biệt!"); return; }
                default -> System.out.println("⚠ Lựa chọn không hợp lệ!");
            }
        }
    }

    private void addContact() {
        System.out.println("=== Thêm mới danh bạ ===");
        String phone = Validation.readPhone();
        if (service.findByPhone(phone) != null) {
            System.out.println("⚠ Số điện thoại đã tồn tại!");
            return;
        }
        String group = Validation.readNonEmpty("Nhóm: ");
        String name = Validation.readNonEmpty("Họ tên: ");
        String gender = Validation.readNonEmpty("Giới tính: ");
        String address = Validation.readNonEmpty("Địa chỉ: ");
        String birth = Validation.readNonEmpty("Ngày sinh: ");
        String email = Validation.readEmail();

        service.add(new Contact(phone, group, name, gender, address, birth, email));
        System.out.println("✅ Thêm thành công!");
    }

    private void updateContact() {
        System.out.println("=== Cập nhật danh bạ ===");
        while (true) {
            System.out.print("Nhập số điện thoại cần sửa (Enter để thoát): ");
            String phone = sc.nextLine().trim();
            if (phone.isEmpty()) return;
            Contact c = service.findByPhone(phone);
            if (c == null) {
                System.out.println("⚠ Không tìm thấy danh bạ này!");
                continue;
            }
            String group = Validation.readNonEmpty("Nhóm mới: ");
            String name = Validation.readNonEmpty("Họ tên mới: ");
            String gender = Validation.readNonEmpty("Giới tính mới: ");
            String address = Validation.readNonEmpty("Địa chỉ mới: ");
            String birth = Validation.readNonEmpty("Ngày sinh mới: ");
            String email = Validation.readEmail();

            c.setGroup(group);
            c.setName(name);
            c.setGender(gender);
            c.setAddress(address);
            c.setBirthDate(birth);
            c.setEmail(email);
            System.out.println("✅ Cập nhật thành công!");
            break;
        }
    }

    private void deleteContact() {
        System.out.println("=== Xóa danh bạ ===");
        while (true) {
            System.out.print("Nhập số điện thoại cần xóa (Enter để thoát): ");
            String phone = sc.nextLine().trim();
            if (phone.isEmpty()) return;
            Contact c = service.findByPhone(phone);
            if (c == null) {
                System.out.println("⚠ Không tìm thấy danh bạ này!");
                continue;
            }
            String confirm = Validation.readYesNo("Bạn có chắc muốn xóa không?");
            if (confirm.equalsIgnoreCase("Y")) {
                service.deleteByPhone(phone);
                System.out.println("✅ Đã xóa!");
            }
            break;
        }
    }

    private void searchContact() {
        System.out.println("=== Tìm kiếm danh bạ ===");
        String keyword = Validation.readNonEmpty("Nhập họ tên hoặc số điện thoại: ");
        List<Contact> results = service.search(keyword);
        if (results.isEmpty()) System.out.println("Không tìm thấy kết quả!");
        else results.forEach(System.out::println);
    }

    private void confirmAndRead() {
        String confirm = Validation.readYesNo("Đọc file sẽ xóa toàn bộ dữ liệu hiện có. Tiếp tục?");
        if (confirm.equalsIgnoreCase("Y")) service.readFromFile();
    }

    private void confirmAndWrite() {
        String confirm = Validation.readYesNo("Ghi file sẽ cập nhật toàn bộ dữ liệu. Tiếp tục?");
        if (confirm.equalsIgnoreCase("Y")) service.writeToFile();
    }
}
