package Model;

public class MessageContact {

    String nom;
    String email;
    int phone;
    String desc;

    public MessageContact() {

    }

    public  MessageContact(String nom, String email, int phone, String desc) {
        this.nom = nom;
        this.email = email;
        this.phone = phone;
        this.desc = desc;
    }

    public void setNom(String nom) { this.nom = nom; }
    public String getNom() { return nom; }

    public void setEmail(String email) {  this.email = email; }
    public String getEmail() { return email; }

    public void setPhone(int phone) { this.phone = phone; }
    public int getPhone() { return phone; }

    public void setDesc(String desc) { this.desc = desc; }
    public String getDesc() { return desc; }
}
