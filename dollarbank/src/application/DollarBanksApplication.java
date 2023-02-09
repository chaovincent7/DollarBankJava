package application;

import java.util.Scanner;

import controller.DollarBanksController;
import model.Account;
import model.Customer;
import utility.ColorsUtility;
import utility.ConsolePrinterUtility;

public class DollarBanksApplication {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		DollarBanksController dbc = new DollarBanksController();
		boolean runApp = true;
		while(runApp) {
			ConsolePrinterUtility.displayMenu();
			try {
				int choice = Integer.parseInt(scan.nextLine());
				// Create New Account
				if(choice==1) {
					ConsolePrinterUtility.SignUp(1);
					System.out.print(ColorsUtility.ANSI_CYAN);
					String cust_name = scan.nextLine();
					System.out.print(ColorsUtility.ANSI_DEFAULT);
					ConsolePrinterUtility.SignUp(2);
					System.out.print(ColorsUtility.ANSI_CYAN);
					String cust_address = scan.nextLine();
					System.out.print(ColorsUtility.ANSI_DEFAULT);
					String phone_num="";
					while(true) {
						ConsolePrinterUtility.SignUp(3); //contact #
						System.out.print(ColorsUtility.ANSI_CYAN);
						phone_num = scan.nextLine();
						if(dbc.validnum(phone_num)) {
							break;
						}
						else {
							System.out.print(ColorsUtility.ANSI_DEFAULT);
							ConsolePrinterUtility.errorOutput(6);
						}
					}
					
					System.out.print(ColorsUtility.ANSI_DEFAULT);
					
					String uid ="";
					while(true) {
						ConsolePrinterUtility.SignUp(4);
						System.out.print(ColorsUtility.ANSI_CYAN);
						uid=scan.nextLine();
						if(!dbc.checkUserId(uid)) {
							System.out.print(ColorsUtility.ANSI_DEFAULT);
							ConsolePrinterUtility.errorOutput(5);
						}
						else {
							break;
						}
					}
					System.out.print(ColorsUtility.ANSI_DEFAULT);
					
					String pw;
					while(true) {
						ConsolePrinterUtility.SignUp(5);
						System.out.print(ColorsUtility.ANSI_CYAN);
						pw = scan.nextLine();
						if(!dbc.checkPassValid(pw)) {
							System.out.print(ColorsUtility.ANSI_DEFAULT);
							ConsolePrinterUtility.errorOutput(2);
						}
						else {
							break;
						}
					}
					
					System.out.print(ColorsUtility.ANSI_DEFAULT);
					
					double initial_balance;
					while(true) {
						ConsolePrinterUtility.SignUp(6);
						System.out.print(ColorsUtility.ANSI_CYAN);
						try {
							initial_balance = Double.parseDouble(scan.nextLine());
							if(initial_balance>0) {
								break;
							}
							else if(initial_balance<=0) {
								System.out.print(ColorsUtility.ANSI_DEFAULT);
								ConsolePrinterUtility.errorOutput(3);
							}
							else {
								System.out.print(ColorsUtility.ANSI_DEFAULT);
								ConsolePrinterUtility.errorOutput(1);
							}
						}
						catch(Exception e) {
							System.out.print(ColorsUtility.ANSI_DEFAULT);
							ConsolePrinterUtility.errorOutput(1);
						}
					}
					
					System.out.print(ColorsUtility.ANSI_DEFAULT);
					dbc.addCustomer(new Customer(cust_name,cust_address,phone_num,uid));
					dbc.addAcc(new Account(uid,pw,initial_balance));
					
				}
				else if(choice==2) {
					//Log In
					boolean loggedIn = false;
					while(true) {
						ConsolePrinterUtility.Login(1);
						System.out.print(ColorsUtility.ANSI_CYAN);
						String entered_id = scan.nextLine();
						System.out.print(ColorsUtility.ANSI_DEFAULT);
						ConsolePrinterUtility.Login(2);
						System.out.print(ColorsUtility.ANSI_CYAN);
						String entered_pw = scan.nextLine();
						if(dbc.checkCredentials(entered_id,entered_pw)) {
							System.out.print(ColorsUtility.ANSI_DEFAULT);
							loggedIn = true;
							break;
						}
						else {
							System.out.print(ColorsUtility.ANSI_DEFAULT);
							ConsolePrinterUtility.errorOutput(4);
						}
					}
					
					
					while(loggedIn) {
						//Print Logged In User Interface
						ConsolePrinterUtility.LoggedIn();
						try {
							int loggedInUserChoice = Integer.parseInt(scan.nextLine());
							//deposit
							if(loggedInUserChoice==1) {								
								while(true) {
									ConsolePrinterUtility.Deposit(1);
									try {
										System.out.print(ColorsUtility.ANSI_CYAN);
										double amountDepo = Double.parseDouble(scan.nextLine());
										if(amountDepo>0) {
											if(dbc.deposit(amountDepo)) {
												System.out.print(ColorsUtility.ANSI_DEFAULT);
												ConsolePrinterUtility.Deposit(2);
												System.out.println(dbc.currBalance());
												break;
											}
										}
										else {
											System.out.print(ColorsUtility.ANSI_DEFAULT);
											ConsolePrinterUtility.Deposit(3);
										}
									}
									catch(Exception e) {
										System.out.print(ColorsUtility.ANSI_DEFAULT);
										ConsolePrinterUtility.Deposit(4);
									}
								}
							}
							//Withdrawal
							else if(loggedInUserChoice==2) {
								while(true) {
									ConsolePrinterUtility.Withdraw(1);
									try {
										System.out.print(ColorsUtility.ANSI_CYAN);
										double amountWithdraw = Double.parseDouble(scan.nextLine());
										if(amountWithdraw>0) {
											if(dbc.withdraw(amountWithdraw)) {
												System.out.print(ColorsUtility.ANSI_DEFAULT);
												ConsolePrinterUtility.Withdraw(2);//success
												System.out.println(dbc.currBalance());
												break;
											}
											else {//unsuccessful
												System.out.print(ColorsUtility.ANSI_DEFAULT);
												ConsolePrinterUtility.Withdraw(3);
												System.out.println("Current Balance is $"+ dbc.currBalance());
											}
										}
										else {
											System.out.print(ColorsUtility.ANSI_DEFAULT);
											ConsolePrinterUtility.Withdraw(5);
										}
									}
									catch(Exception e) {
										System.out.print(ColorsUtility.ANSI_DEFAULT);
										ConsolePrinterUtility.Withdraw(4);
									}
								}
							}
							//Transfer Funds
							else if(loggedInUserChoice==3) {
								while(true) {
									ConsolePrinterUtility.Transfer(1);
									System.out.print(ColorsUtility.ANSI_CYAN);
									String tid=scan.nextLine();
									System.out.print(ColorsUtility.ANSI_DEFAULT);
									if(!dbc.transferCheckId(tid)) {
										ConsolePrinterUtility.Transfer(3);
									}
									else {
										while(true) {
											ConsolePrinterUtility.Transfer(2);
											try {
												System.out.print(ColorsUtility.ANSI_CYAN);
												double amountTransfer = Double.parseDouble(scan.nextLine());
												if(amountTransfer>0) {
													if(dbc.transferCheck(amountTransfer)) {
														System.out.print(ColorsUtility.ANSI_DEFAULT);
														//Transfer of the money
														dbc.transfer(tid,amountTransfer);
														ConsolePrinterUtility.Transfer(4);
														System.out.println(dbc.currBalance());
														break;
													}
													//invalid
													else {
														System.out.print(ColorsUtility.ANSI_DEFAULT);
														ConsolePrinterUtility.Transfer(7);
														System.out.println("Current Balance is $"+ dbc.currBalance());
													}
												}
												else {
													System.out.print(ColorsUtility.ANSI_DEFAULT);
													ConsolePrinterUtility.Transfer(6);
												}
											}
											catch(Exception e) {
												System.out.print(ColorsUtility.ANSI_DEFAULT);
												ConsolePrinterUtility.Transfer(5);
											}
										}
										break;
									}
									
								}
							}
							else if(loggedInUserChoice==4) {
								dbc.recentTransactions();
							}
							else if(loggedInUserChoice==5) {
								dbc.customerInfo();
							}
							else if(loggedInUserChoice==6) {
								System.out.println("You have signed out!");
								loggedIn = false;
							}
							else {
								ConsolePrinterUtility.errorOutput(1);
							}
						}
						catch(Exception e) {
							ConsolePrinterUtility.errorOutput(1);
						}
					}
				}
				else if(choice ==3) {
					//Exit
					runApp = false;
					System.out.println("Thank you for using the DOLLARSBANK Application! Goodbye!");
				}
				else {
					ConsolePrinterUtility.errorOutput(1);
				}
			}
			catch(Exception e){
				ConsolePrinterUtility.errorOutput(1);
			}
	}
		scan.close();

}
}
