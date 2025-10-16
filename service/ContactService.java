package service;

import model.Contact;
import java.io.*;
import java.util.*;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();
    private static final String FILE_PATH = "data/contacts.csv";

    // Hiển thị danh sách 5 dòng / trang
    public void showContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Danh bạ trống!");
            return;
        }
        System.out.printf("%-12s | %-10s | %-20s | %-8s | %-20s | %-12s | %-25s\n",
                "SĐT", "Nhóm", "Họ tên", "Giới tính", "Địa chỉ", "Ngày sinh", "Email");
        System.out.println("-------------------------------------------------------------------------------------------");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println(contacts.get(i));
            if ((i + 1) % 5 == 0 && i < contacts.size() - 1) {
                System.out.print("Nhấn Enter để xem tiếp...");
                new Scanner(System.in).nextLine();
            }
        }
    }

    public void add(Contact c) {
        contacts.add(c);
    }

    public Contact findByPhone(String phone) {
        for (Contact c : contacts)
            if (c.getPhoneNumber().equals(phone)) return c;
        return null;
    }

    public boolean deleteByPhone(String phone) {
        return contacts.removeIf(c -> c.getPhoneNumber().equals(phone));
    }

    public List<Contact> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(keyword) ||
                    c.getPhoneNumber().contains(keyword))
                result.add(c);
        }
        return result;
    }

    public void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            contacts.clear();
            br.readLine(); // bỏ dòng tiêu đề
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    contacts.add(new Contact(
                            data[0], data[1], data[2], data[3], data[4], data[5], data[6]
                    ));
                }
            }
            System.out.println("✅ Đọc file thành công!");
        } catch (IOException e) {
            System.out.println("⚠ Lỗi đọc file: " + e.getMessage());
        }
    }

    public void writeToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            pw.println("phoneNumber,group,name,gender,address,birthDate,email");
            for (Contact c : contacts) {
                pw.printf("%s,%s,%s,%s,%s,%s,%s\n",
                        c.getPhoneNumber(), c.getGroup(), c.getName(),
                        c.getGender(), c.getAddress(), c.getBirthDate(), c.getEmail());
            }
            System.out.println("✅ Ghi file thành công!");
        } catch (IOException e) {
            System.out.println("⚠ Lỗi ghi file: " + e.getMessage());
        }
    }

    public List<Contact> getAll() {
        return contacts;
    }
}
