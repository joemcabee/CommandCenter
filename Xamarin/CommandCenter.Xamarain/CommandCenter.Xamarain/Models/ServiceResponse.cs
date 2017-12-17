using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CommandCenter.Xamarain.Models
{
    public class ServiceResponse
    {
        public string Service { get; set; }
        public bool Success { get; set; }
        public int StatusCode { get; set; }
        public string Data { get; set; }
        public Exception Exception { get; set; }
    }
}
