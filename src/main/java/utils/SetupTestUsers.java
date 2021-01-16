package utils;


import entities.Author;
import entities.Book;
import entities.Library;
import entities.Loan;
import entities.Member;
import entities.Role;
import entities.User;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    /*User user = new User("user", "test1");
    User admin = new User("admin", "test2");
    User both = new User("user_admin", "test3");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");*/
   
    
    Library l1 = new Library("Gladsaxe Bibliotek");
    
    Author a1 = new Author("Neil Gaiman");
    Author a2 = new Author("J. K. Rowling");
    Author a3 = new Author("Terry Pratchett");
    Author a4 = new Author("Anton LaVey");
    
    Book b1 = new Book(123456789, "Coraline", "Something", 2003);
    Book b2 = new Book(6356, "Harry Potter and The Chamber of Secrets", "Sure", 1995);
    Book b3 = new Book(5839, "Good Omens", "Angel inc", 2002);
    Book b4 = new Book(666, "The Satanic Bible", "Devil inc", 2001);
    
    //User user = new User("Lou", "123");
    Member member = new Member("Lou", "nope");
    
    //Date date = new Date();
    
    Loan loan = new Loan(new Date());
      System.out.println("Setup:");
      System.out.println(loan);
    b4.addLoan(loan);
    member.AddMemLoans(loan);
    
    
    l1.addBook(b1);
    l1.addBook(b2);
    l1.addBook(b3);
    l1.addBook(b4);
    
    b1.addAuthor(a1);
    b2.addAuthor(a2);
    b3.addAuthor(a1);
    b3.addAuthor(a3);
    b4.addAuthor(a4);
    
    em.getTransaction().begin();
    em.persist(a4);
    em.persist(loan);
    em.persist(member);
    em.persist(b4);
    /*em.persist(l1);
    em.persist(a1);
    em.persist(a2);
    em.persist(a3);
    em.persist(b1);
    em.persist(b2);
    em.persist(b3);*/
    em.getTransaction().commit();
      System.out.println("Done");
  }

}
