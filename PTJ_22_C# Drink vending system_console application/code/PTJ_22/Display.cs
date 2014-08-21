using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PTJ_22
{
    /* This is the view of our system */
    class Display
    {
        /* Show the system main menu according to the option */
        public static void ShowMenu(int option)
        {
            switch (option)
            {
                case 0:
                    Console.WriteLine("Welcome to Ezi Drink AVM");
                    break;
                case 1:
                    Console.WriteLine("Please choose");
                    break;
                case 2:
                    Console.WriteLine("You can choose to collect your drink or obtain a refund of your coins");
                    break;
            }
            
            Console.WriteLine("------------------------------------------");
            Console.WriteLine("      1.Select Drink");
            Console.WriteLine("      2.Insert Coins");
            Console.WriteLine("      3.Refund Coins");
            Console.WriteLine("      4.Collect Drink");
            Console.WriteLine("      5.Exit Simulation\n");
            Console.Write("Please enter your choice<1-5>:");
        }

        /* Show the dinks which the system have */
        public static void ShowDrink()
        {
            Console.WriteLine("1.Soft Drink");
            Console.WriteLine("2.Milk Dink");
            Console.WriteLine("3.Fruit Dink");
            Console.WriteLine("4.Cancel Selection\n");
            Console.Write("Please select one type of drink<1-4>");
        }

        /* Show the costs of the drink which the user choose */
        public static double ShowCost(int option)
        {
            Console.Write("Your drink costs ");
            double cost;
            switch (option)
            {
                case 1: 
                    Console.WriteLine("$1.50");
                    cost = 1.5;
                    break;
                case 2: 
                    Console.WriteLine("$3.00");
                    cost = 3.0;
                    break;
                default:
                    Console.WriteLine("$2.20");
                    cost = 2.20;
                    break;
            }

            return cost;
        }

        /* Show the denominations of acceptable coins and show balance of cost */
        public static void ShowCoin()
        {
            Console.WriteLine("2.0 = $2.00");
            Console.WriteLine("1.0 = $1.00");
            Console.WriteLine("0.5 = $0.50");
            Console.WriteLine("0.2 = $0.20");
            Console.WriteLine("0.1 = $0.10\n");
        }

        /* Enter a coin */
        public static void ShowEnterCoin()
        {
            Console.Write("Please enter a coin value or 0 to Cancel: ");
        }

        /* Show balance costs */
        public static void ShowBalanceCost(double balanceCost)
        {
            Console.WriteLine("Balance of cost ${0:F2}\n", balanceCost);
        }

        /* Show refund coins */
        public static void ShowRefund(double amountEntered)
        {
            if(amountEntered != 0)
                Console.WriteLine("Please collect your change of ${0:F2}\n", amountEntered);
            else
                Console.WriteLine("Sorry! You have not entered any coins\n");
        }

        /* Show the message of collect drinks */
        public static void ShowCollectDrink()
        {
            Console.WriteLine("Please collect your drink. Enjoy.\n");
        }

        public static void ExitSystem()
        {
            Console.WriteLine("Thank you for using Ezi Drink AVM\n\n");
            Console.WriteLine("Press any key to terminate simulation ...");
        }

        /* If user don't select a drink but choose other item, show this message */
        public static void ShowErrSelected()
        {
            Console.WriteLine("Please select a drink first!\n");
        }

        /* If user input wrong denominations of coins, warn he/she */
        public static void ShowErrDenominations()
        {
            Console.WriteLine("Please enter right denominations of acceptable coins!\n");
        }

        /* If user don't input enough coins to collect the drinks, warn he/she */
        public static void ShowErrBalance()
        {
            Console.WriteLine("Sorry! You have not yet paid for this drink\n");
        }

        /* If user choose a wrong option, warn he or she */
        public static void ShowErrOption()
        {
            Console.WriteLine("Please enter right Option!\n");
        }
    }
}
