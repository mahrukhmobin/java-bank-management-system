import java.util.ArrayList;

class Person{
    private String Name;
    private String CNIC;
    private String phoneNo;

    public String getName() {return Name;}
    public void setName(String name) {Name = name;}
    public String getCNIC() {return CNIC;}
    public void setCNIC(String CNIC) {this.CNIC = CNIC;}
    public String getPhoneNo() {return phoneNo;}
    public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}


    public String toString(){
        return "\n     Name: "+Name+"\n     CNIC: "+CNIC+"\n     Phone Number: "+phoneNo+"\n";

    }

    public Person(String Name,String CNIC,String phoneNo){
        this.Name=Name;
        this.CNIC=CNIC;
        this.phoneNo=phoneNo;
    }

    public Person(){
    }
}




class Account{
    private String Number;
    private float Amount;
    private Client AcHolder;

    static int count=0;

    public String getNumber() {return Number;}
    public void setNumber(String number) {Number = number;}
    public float getAmount() {return Amount;}
    public void setAmount(float amount) {Amount = amount;}
    public Client getAcHolder() {return AcHolder;}
    public void setAcHolder(Client acHolder) {AcHolder = acHolder;}

    public Account(float Amount,Client AcHolder){
        this.Amount=Amount;
        this.AcHolder=AcHolder;
        this.Number="A"+(++count);
    }


    public float withdraw(float amount){
        if(amount<=Amount)
            Amount -= amount;
        else
            System.out.println("Insufficient Balance!");
        return Amount;
    }

    public float deposit(float amount){
        Amount += amount;
        return Amount;
    }

    public String toString(){
        return "\nAccount number: "+Number+"\nAmount: "+Amount+"\nAccount Holder: "+AcHolder.getPersonDetails().getName();
    }

}



class Client{
    private String id;
    private Person PersonDetails;
    private ArrayList<Account> AcList;

    static int count=0;

    public String getid() {return id;}
    public void setid(String id) {this.id = id;}
    public Person getPersonDetails() {return PersonDetails;}
    public void setPersonDetails(Person personDetails) {PersonDetails = personDetails;}
    public ArrayList<Account> getAcList() {return AcList;}
    public void setAcList(ArrayList<Account> acList) {AcList = acList;}

    public Client(Person PersonDetails){
        this.PersonDetails=PersonDetails;
        this.id="C"+(++count);
        AcList=new ArrayList<>();
    }

    public void addAccount(Account a){
        AcList.add(a);
    }

    public void deposit(float amount, String accNo){
        for (Account a: AcList)
            if(a.getNumber().equals(accNo)){
                a.deposit(amount);
                return;
            }
        System.out.println("Account not Found!");

    }

    public void withdraw(float amount, String accNo){
        for(Account a: AcList)
            if(a.getNumber().equals(accNo)){
                a.withdraw(amount);
                return;
            }
        System.out.println("Account not Found!");
    }

    public float totalAmount(){
        float sum=0;
        for(Account a : AcList)
            sum+=a.getAmount();
        return sum;
    }

    public String toString(){
        String s ="\nClient id: "+id+"\nPersonal Details: "+ PersonDetails.toString()+"\nAccounts: ";
        for (Account a:AcList)
            s += "\n     Amount in Account "+a.getNumber()+" : "+a.getAmount();
        return s;
    }

}

class Bank{
    private String Name;
    private ArrayList<Client> ClList;
    private ArrayList<Account> AcList;

    public String getName() {return Name;}
    public void setName(String name) {Name = name;}
    public ArrayList<Client> getClList() {return ClList;}
    public void setClList(ArrayList<Client> clList) {ClList = clList;}
    public ArrayList<Account> getAcList() {return AcList;}
    public void setAcList(ArrayList<Account> acList) {AcList = acList;}

    public Bank(String Name){
        this.Name=Name;
        ClList=new ArrayList<>();
        AcList=new ArrayList<>();
    }

    public Client addClient(Person p){
        Client c = new Client(p);
        ClList.add(c);
        return c;
    }

    public Account addAccount(String id, float amount, Client c){
        Account a = new Account(amount,c);
        AcList.add(a);
        c.addAccount(a);
        return a;
    }

    public Account searchAccount(String id){
        for(Account a: AcList)
            if(a.getNumber().equals(id)){
                return a;
            }
        return null;
    }

    public Boolean removeClient (String id){
        for(Client c: ClList)
            if(c.getid().equals(id)){
                AcList.removeIf(a->a.getAcHolder()==c);
                ClList.remove(c);
                return true;
            }
        return false;
    }

    public float totalAmount(){
        float sum=0;
        for (Account a: AcList)
            sum += a.getAmount();
        return sum;
    }

    public Client searchCustomerDetail(String CNIC){
        for(Client c : ClList)
            if(c.getPersonDetails().getCNIC().equals(CNIC)){
                return c;
            }
        return null;

    }

    public String toString(){
        return "Bank: " + Name + "\nTotal Clients: " + ClList.size() + "\nTotal Accounts: " + AcList.size();
    }

}

public class Main {
    public static void main(String[] args){

        Bank b=new Bank("UET Bank");

        Person p1=new Person("Iffa","12345-678910","03214402388");
        Person p2=new Person("Maryam","12345-109876","03214041439");

        Client c1=b.addClient(p1);
        Client c2=b.addClient(p2);

        Account a1=b.addAccount(c1.getid(),2000,c1);
        Account a2=b.addAccount(c2.getid(),3000,c2);
        Account a3=b.addAccount(c2.getid(),1000,c2);

        c1.deposit(2000,a1.getNumber());
        c2.withdraw(500,a3.getNumber());

        System.out.println(c1);
        System.out.println("\n------------------------------");
        System.out.println(c2);
        System.out.println("\n------------------------------");

        System.out.println("Total Amount in Bank: "+b.totalAmount());

        Account x=b.searchAccount(a2.getNumber());
        System.out.println("Searched Account Details: "+x);

        b.removeClient(c1.getid());
        System.out.println("Client 1 Removed Sucessfully!");
        System.out.println("Total Amount Remaining in Bank: "+b.totalAmount());

    }
}