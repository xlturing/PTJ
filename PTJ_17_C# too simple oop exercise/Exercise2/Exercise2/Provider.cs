using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Exercise2
{
    class Provider
    {
        private List<Resource> lr;  // A collection of zero to four instances of the Resource class
        public Provider()
        {
            lr = new List<Resource>();
        }

        /* Addition resource member function */
        public void addRes(Resource r)
        {
            lr.Add(r);
        }

        /* Removal  resource at index i member function */
        public void removeRes(int i)
        {
            lr.RemoveAt(i);
        }

        /* The number of Resources in this provider */
        public int countRes()
        {
            return lr.Count;
        }
    }
}
