using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CommandCenter.Xamarain.Models
{
    public class ServiceRequest
    {
        override public string ToString()
        {
            string value = this.Url;

            if (value.EndsWith("/"))
            {
                value += this.Service;
            }
            else
            {
                value += "/" + this.Service;
            }

            return value;
        }

        public string Url { get; set; }
        public string Service { get; set; }
        //public string Username { get; set; }
        //public string Password { get; set; }
        public string AuthHash { get; set; }
        public string Data { get; set; }

        //public string GetAuthHash()
        //{
        //    var authData = string.Format("{0}:{1}", this.Username, this.Password);
        //    var authHeaderValue = Convert.ToBase64String(Encoding.UTF8.GetBytes(authData));

        //    return String.Format("Basic {0}", authHeaderValue);
        //}
    }
}
