import java.sql.*;
import java.util.Scanner;
import java.sql.*;

class Book
{
	String isbn;
	String name;
	String author;
	int price;

	public Book readBookFromUser()
        {
                Book book = new Book();
                Scanner input = new Scanner(System.in);
                System.out.println("Enter isbn");
                book.isbn = input.next();
                System.out.println("Enter name of the book");
                book.name = input.next();
                System.out.println("Enter author");
                book.author = input.next();
                System.out.println("Enter price");
                int price = input.nextInt();
                return book;
        }
}

class BookMgmt
{
	private String driver = "com.mysql.jdb.Driver";
	private String url = "jdbc:mysql://localhost:3306/bookstore";
	private String user = "root";
	private String password = "Admin@123";
	private Connection conn;

	public static void main(String Args[])
	{
		System.out.println("welcome to Bookstore");
		
		BookMgmt bookMgmt = new BookMgmt();

		boolean wannExit = false;

		System.out.println("Welcome to book store");

		do{
			System.out.println("***************************************************");
			System.out.println("1.List og books available\n 2.Add a Book\n 3.Delete a Book\n 4.Search for A book\n 5.Modify A book detail");
			System.out.println("Press any Other Key to Quit\n");
			java.util.Scanner input = new java.util.Scanner(System.in);
			int choice= input.nextInt();
			
			switch(choice)
			{
				case 1:	ResultSet rs = bookMgmt.listBooks();
					System.out.println("The available books are ");
					/*
						loop thru the resultSet
					*/
					try{
						while(rs.next())
						{
							String isbn = rs.getString("isbn");
							String name = rs.getString("bookName");
							String author = rs.getString("author");
							Float price = rs.getFloat("price");
						
							System.out.println("Isbn : " + isbn + "Book Name :" + name + " Author : " + author + " Price : " + price);
						}
					}catch(SQLException ex){
						ex.printStackTrace();
					}
					
					break;
				case 2:
                                        System.out.println("Enter the book details to be inserted");
					Book objBook = new Book();
					
					Boolean result1 = bookMgmt.insert(objBook.readBookFromUser());
					if(result1)
						System.out.println("Book has added to store");
					else   
						System.out.println("Sorry!!! couldn't add book to store\n");
					break;

				case 3:
                                        System.out.println("enter isbn of book to be removed");
					Scanner in =new Scanner(System.in);
					Boolean result2 = bookMgmt.remove(in.next());
					if(result2)
						System.out.println("Book Successfully Removed from store\n");
					else	System.out.println("Failed to remove Book\n");

					break;
				case 4:
                                        System.out.println("Enter the name of of the book to search");
					Scanner input2=new Scanner(System.in);
					String name=input2.next();
					ResultSet rs2 = bookMgmt.search(name);
					break;
					/* coming up soon*/

				case 5: System.out.println("Enter book details to be updated for existing book");
					 objBook = new Book();
					
					Boolean updatResult = bookMgmt.update(objBook.readBookFromUser());
					if(updatResult)
						System.out.println("UpdatedSuccessfully\n");
					else
						System.out.println("failed toupdate\n");

					break;
				default: wannExit = true;
			}
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		}while(!wannExit);
	}
	
	public ResultSet listBooks()
	{
		ResultSet rs = null;
		try
		{
			conn = DriverManager.getConnection(url,user,password);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM tblBook");
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return rs;
	}
	
	public Boolean insert(Book book)
	{
		//boolean result = false;
		int noOfRowsAffected = 0;

		try
                {
                        conn = DriverManager.getConnection(url,user,password);
                        Statement stmt = conn.createStatement();
			String query = "INSERT INTO tblBook(isbn,bookName,author,price) values('"+book.isbn+"','"+book.name+"','"+book.author+"','"+book.price+"')";
                        noOfRowsAffected = stmt.executeUpdate(query);
                }catch(SQLException ex){
                        ex.printStackTrace();
                }
		if(noOfRowsAffected > 0)
			return true;	
		return false;
	}
	
	public Boolean update(Book book)
	{
		return false;
	}

	public Boolean remove(String isbn)
	{
		return false;
	}

	public ResultSet search(String name)
	{
		return null;
	}
}
