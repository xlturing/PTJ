using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Exercise1
{
    class Exercise1
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Please input two integer");
            int a, b;
            string s1, s2;
            s1 = Console.ReadLine();
            s2 = s1.Split()[1];
            s1 = s1.Split()[0];

            /* Include robust error handling */
            try
            {
                a = int.Parse(s1);
                b = int.Parse(s2);
            }
            catch (Exception e)
            {
                Console.WriteLine("Either number is not an integer");
                /* Hold on to look at the result */
                Console.ReadLine();
                return;
            }
            if (a < 2)
                Console.WriteLine("First number < 2");
            else if (b == 0)
                Console.WriteLine("Second number = 0");
            else if (b > a)
                Console.WriteLine("Second number > first number");
            else if (a < 0 || b < 0)
                Console.WriteLine("Either number is negative");
            else if (a >= 1000)
                Console.WriteLine("First number must be < 1000");
            /* Key part: handle the data */
            else
            {
                while (a > 0)
                {
                    if (a % b == 0)
                        Console.Write(a + " ");
                    --a;
                }
            }

            /* Hold on to look at the result */
            Console.ReadLine();
        }
    }
}
