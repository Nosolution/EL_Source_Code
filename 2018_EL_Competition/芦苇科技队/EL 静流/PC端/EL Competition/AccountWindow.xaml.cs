using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Threading;
using System.IO;
namespace EL_Competition
{
    /// <summary>
    /// AccountWindow.xaml 的交互逻辑
    /// </summary>
    public partial class AccountWindow : Window
    {
        public AccountWindow()
        {
            
            InitializeComponent();
            usernameText.Text = username;
            back = new Button() { Margin=new Thickness(0,0,30,10),Visibility = Visibility.Hidden, BorderBrush = Brushes.Purple, VerticalAlignment = VerticalAlignment.Bottom, HorizontalAlignment = HorizontalAlignment.Right, Foreground = Brushes.Purple, Width = 40, Height = 20, Background = Brushes.Transparent, Content = "返回" };
            back.Click += Back_Click;
            grid.Children.Add(back);
        }
        public string username;
        string SaveLoc = Model1Setup.SaveLoc;
        Button back;
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            List<string> things = new List<string>();
            StackPanel record = new StackPanel();
            using (StreamReader re = new StreamReader(SaveLoc + @"/" + username + ".ini"))
            {
                while (re.Peek() >= 0)
                {
                    things.Add(re.ReadLine());
                }
                things.Remove(things[0]);
            }
            foreach (string i in things)
            {

                string[] a = i.Split(' ');
                string te = "\n";
                foreach(string j in a)
                {
                    te += j + "\n";
                }
                TextBlock t = new TextBlock() { Text = te };
                record.Children.Add(t);
            }

            
            recorder.Content = record;
            
            Thread ani = new Thread(() =>
            {
                for (int i = 300; i >= 0; i-=3)
                {
                    Dispatcher.Invoke(new Action(() =>
                    {
                        recorder.Margin = new Thickness(i,0,0,0);
                    }));
                    Thread.Sleep(8);
                }
                Dispatcher.Invoke(new Action(() =>
                {
                    back.IsEnabled = true;
                    back.Visibility = Visibility.Visible;
                }));
            });
            ani.Start();
        }

        private void Back_Click(object sender, RoutedEventArgs e)
        {
            back.Visibility = Visibility.Hidden;
            Thread ani = new Thread(() =>
            {
                Dispatcher.Invoke(new Action(() =>
                {
                    back.IsEnabled = false;
                }));
                for (int i = 0; i <= 300; i += 3)
                {
                    Dispatcher.Invoke(new Action(() =>
                    {
                        recorder.Margin = new Thickness(i, 0, 0, 0);
                    }));
                    Thread.Sleep(8);
                }
                
            });
            ani.Start();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            if (MessageBox.Show("您确定要清除记录么？", "专注小助手", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
            {
                string tem;
                using (StreamReader re = new StreamReader(SaveLoc + @"/" + username + ".ini"))
                {
                    tem = re.ReadLine();
                }
                using (StreamWriter wr = new StreamWriter(SaveLoc + @"/" + username + ".ini"))
                {
                    wr.WriteLine(tem);
                }
                MessageBox.Show("清除成功");
            }
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            if (MessageBox.Show("您确定要删除账号么？", "专注小助手", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
            {
                File.Delete(SaveLoc + @"/" + username + ".ini");
                MessageBox.Show("删除成功");
                Close();
            }
        }

        private void Button_Click_3(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("抱歉，好友功能还未上线>_<");
        }

        private void Button_Click_4(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
