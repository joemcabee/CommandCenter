using CommandCenter.Xamarain.Models;
using Newtonsoft.Json;
using System;
using System.Diagnostics;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace CommandCenter.Xamarain
{
    public class ApiClient
    {
        HttpClient _client;

        public ApiClient()
        {
            _client = new HttpClient();
            _client.MaxResponseContentBufferSize = 256000;
            _client.Timeout = new TimeSpan(0, 0, 10);
        }

        public async Task<ServiceResponse> Get(ServiceRequest request)
        {
            _client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", request.AuthHash);

            var result = new ServiceResponse();

            // RestUrl = http://developer.xamarin.com:8081/api/ServiceResponses)
            var endpoint = String.Concat(Urls.CommandCenter, request.Service);
            Uri uri = null;

            try
            {
                uri = new Uri(endpoint);
            }
            catch(Exception ex)
            {
                Debug.WriteLine(String.Format("Error creating URI from {0}.", endpoint));
                Debug.WriteLine("Exception: {0}.", ex.ToString());
            }

            try
            {
                var response = await _client.GetAsync(uri);
                result.Success = response.IsSuccessStatusCode;
                result.StatusCode = (int)response.StatusCode;

                if (response.IsSuccessStatusCode)
                {
                    var content = await response.Content.ReadAsStringAsync();
                    result.Data = content;
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(@"ERROR at {0}. Message: {1}", endpoint, ex.Message);
                result.Success = false;
                result.Exception = ex;
            }

            return result;
        }

        public async Task<ServiceResponse> Post(ServiceRequest request)
        {
            // RestUrl = http://developer.xamarin.com:8081/api/ServiceResponses
            var endpoint = String.Concat(Urls.CommandCenter, request.Service);
            var uri = new Uri(endpoint);
            var result = new ServiceResponse();

            try
            {
                var json = JsonConvert.SerializeObject(request.Data);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                HttpResponseMessage response = null;

                response = await _client.PostAsync(uri, content);
                result.Success = response.IsSuccessStatusCode;
                result.StatusCode = (int)response.StatusCode;

                if (response.IsSuccessStatusCode)
                {
                    Debug.WriteLine(@"ServiceResponse successfully saved.");
                    var responseContent = await response.Content.ReadAsStringAsync();
                    result.Data = responseContent;
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(@"ERROR {0}", ex.Message);
                result.Success = false;
                result.Exception = ex;
            }

            return result;
        }

        public async Task Delete(string id)
        {
            // RestUrl = http://developer.xamarin.com:8081/api/ServiceResponses/{0}
            var uri = new Uri(string.Format(Urls.CommandCenter, id));

            try
            {
                var response = await _client.DeleteAsync(uri);

                if (response.IsSuccessStatusCode)
                {
                    Debug.WriteLine(@"ServiceResponse successfully deleted.");
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(@"ERROR {0}", ex.Message);
            }
        }
    }
}

