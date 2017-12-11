package com.bank;

import java.io.*;
import java.util.*;

public class App {
	static List<Account> accountList = new ArrayList<Account>();
	static Scanner sc = new Scanner(System.in);

    public static void main( String[] args ) {
		String name = "";
		String accountInteraction = "";
				

		storeAccounts();
		
		System.out.println("Input name of account you want to access. If you want to create a new account, type in 'create'. If you want to exit, type in 'exit'.");
		
		int accountLocation = -1;
		
		do {
			name = sc.nextLine();
			for (Account account : accountList) {
				if (account.checkName(name)) {
					account.getInfo(name);
					accountLocation = accountList.indexOf(account);
				} else if (name.toLowerCase().equals("exit")) {
					accountLocation = -2;
				} else if (name.toLowerCase().equals("create")) {
					accountLocation = -3;
				}
			}

			if (accountLocation == -1) {
				System.out.println("Please try again.");
			} 
		} while (accountLocation == -1);

		if (accountLocation == -3) {
			createAccount();
		} else if (accountLocation >= 0){
			System.out.println("Would you like to withdraw or deposit?");
			accountInteraction = sc.nextLine();
			if (accountInteraction.toLowerCase().equals("withdraw")) {
				withdrawFrom(accountLocation);
			} else if (accountInteraction.toLowerCase().equals("deposit")) {
				depositTo(accountLocation);
			}
			System.out.println("");
			System.out.println("Thank you for your continued patronage.");
		}

	}
	
	public static void storeAccounts() {
		try(FileReader fr = new FileReader("Accounts.txt")) {
			int i;
			char stopper = ';';
			String accountLine = "";

			while ((i = fr.read()) != -1) {
				if ((char) i == stopper) {
					Account newAccount = new Account();
					newAccount.setInfo(accountLine);
					accountList.add(newAccount);
					accountLine = "";
				}
				else {
					accountLine += (char) i;
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	static private void createAccount() {
		String newName;
		double newBalance;
		double newInterest;

		System.out.println("Please input your name.");
		newName = sc.nextLine();
		if (newName.equals("exit")) {
			System.exit(0);
		}
		
		System.out.println("Please input your balance.");
		newBalance = Double.parseDouble(sc.nextLine());

		System.out.println("Please input your interest rate.");
		newInterest = Double.parseDouble(sc.nextLine());
		
		try (FileWriter fw = new FileWriter("Accounts.txt", true)) {
			fw.write("\n" + newName + "," + newBalance + "," + newInterest + ";");
			fw.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

		System.out.println("");
		System.out.println("Account CREATED.");
		System.out.println(newName);
		System.out.println(String.format("$%,.2f", newBalance));
		System.out.println(String.format("%,.2f", newInterest) + "% interest rate");
	}

	static private void withdrawFrom(int accountLocation) {
		System.out.println("Please enter the amount of money you would like to withdraw.");
		double withdraw = Double.parseDouble(sc.nextLine());
		accountList.get(accountLocation).withdraw(withdraw);
		
		try (FileWriter fw = new FileWriter("Accounts.txt")) {
			for (Account account : accountList) {
				String record = account.prepareLine();
				fw.write(record);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	static private void depositTo(int accountLocation) {
		System.out.println("Please enter the amount of money you would like to deposit.");
		double deposit = Double.parseDouble(sc.nextLine());
		accountList.get(accountLocation).deposit(deposit);
		
		try (FileWriter fw = new FileWriter("Accounts.txt")) {
			for (Account account : accountList) {
				String record = account.prepareLine();
				fw.write(record);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
