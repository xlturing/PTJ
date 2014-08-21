using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PTJ_22
{
    class VendingMachine
    {
        public double amountEntered = 0;
        public double drinkCost = 0;
        public bool drinkSelected = false;  // The original selected state of the drink is false
        public const int EXIT = 5;

        /* Action: Option corresponds to a valid choice from the main menu, and the corresponding menu option is called */
        public void PerformOption(int option)
        {
            if (option == EXIT)
            {
                Display.ExitSystem();
                Console.Read();
                System.Environment.Exit(0); // Close the console
            }
            else if (option == 1)
            {
                SelectProduct();
                drinkSelected = true;
            }
            else
            {
                if (!drinkSelected)
                {
                    Display.ShowErrSelected();
                    Display.ShowMenu(1);
                }
                else
                {
                    switch (option)
                    {
                        case 2:
                            InsertCoin();
                            break;
                        case 3:
                            RefundCoins();
                            break;
                        case 4:
                            CollectProduct();
                            break;
                        default:    // invalid enter option
                            Display.ShowErrOption();
                            break;
                    }
                }

            }
        }

        /* Action: User has selected a drink and the user enters coins to the value of the drink or the user decides to cancel inserting coins */
        public void InsertCoin()
        {
            Display.ShowCoin();
            Display.ShowBalanceCost(drinkCost - amountEntered);
            while (drinkCost > amountEntered)
            {
                Display.ShowEnterCoin();
                double enterCoin = double.Parse(Console.ReadLine());    // input the coin
                if (enterCoin != 2.0 && enterCoin != 1.0 && enterCoin != 0.5 && enterCoin != 0.2 && enterCoin != 0.1)
                {
                    Display.ShowErrDenominations();
                    continue;
                }
                if (enterCoin == 0) // If user input 0, he/she choose cancel, so break
                    break;
                amountEntered += enterCoin;
                Display.ShowBalanceCost(drinkCost - amountEntered);
            }
            if(drinkCost<=amountEntered)
                Display.ShowMenu(2);    // tell user can collect drink or refund all coins
        }

        /* Action: Returns a valid coin value or 0 */
        public double GetCoin()
        {
            return amountEntered;
        }

        /* Action: User selects a product */
        public void SelectProduct()
        {
            Display.ShowDrink();    // Show all type of drinks
            int option = int.Parse(Console.ReadLine());
            if (option < 1 || option > 4)
            {
                Display.ShowErrOption();
                Display.ShowMenu(1);
            }
            else if (option == 4)
            {
                // cancel, then show main menu and return
                Display.ShowMenu(1);
                return;
            }
            else
            {
                double cost = Display.ShowCost(option);   // Show the cost of the choice drink
                drinkCost += cost;      // add to the drink costs
                Display.ShowMenu(1);    // Then show the main menu
            }
        }

        /* Action: User obtains a refund or receives change. */
        public void RefundCoins()
        {
            Display.ShowRefund(amountEntered);
            amountEntered = 0;  // Clear the amout of the coins which user entered
            Display.ShowMenu(1);
        }

        /* Action: User collects the drink */
        public void CollectProduct()
        {
            if (amountEntered >= drinkCost)
            {
                Display.ShowCollectDrink();
                amountEntered -= drinkCost;
                if (amountEntered > 0)
                    RefundCoins();
                drinkCost = 0;
                drinkSelected = false;
            }
            else
            {
                Display.ShowErrBalance();
                Display.ShowMenu(1);    // show the menu again
            }
        }

        public static void Main(String[] args)
        {
            // init the vending machine
            VendingMachine vm = new VendingMachine();
            Display.ShowMenu(0);
            
            while (true)
            {
                int option = 0; // Get user input choice
                try
                {
                    option = int.Parse(Console.ReadLine());
                    if (option > 5 || option < 1)
                        throw new Exception();
                    vm.PerformOption(option);
                }
                catch (Exception e)
                {
                    // if user input wrong, warn it
                    Display.ShowErrOption();
                    Display.ShowMenu(1);
                    continue;
                }
            }
        }
    }
}
