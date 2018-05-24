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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.IO;

namespace EL_Competition
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        
        public MainWindow()
        {
            string SaveLoc = Model1Setup.SaveLoc;
            InitializeComponent();

            if (File.Exists(SaveLoc + @"\setting.ini"))
            {
                Model1 a = new Model1();
                a.Show();
                this.Close();
            }
            if (File.Exists(SaveLoc + @"\setting2.ini"))
            {
                Model2 b = new Model2();
                b.Show();
                this.Close();
            }
        }

        private void model1_Click(object sender, RoutedEventArgs e)
        {
            Model1Setup setup = new Model1Setup();
            setup.issigned = isSigned;
            setup.username = username;
            setup.Show();
            this.Close();
        }

        private void model2_Click(object sender, RoutedEventArgs e)
        {
            Model2setup setup = new Model2setup();
            setup.issigned = isSigned;
            setup.username = username;
            setup.Show();
            this.Close();
        }

        private void Grid_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            this.DragMove();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }
        public string username;
        public bool isSigned = false;
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            if (!isSigned)
            {
                SignInWindow a = new SignInWindow();
                a.ShowDialog();
                if (a.hasSigned())
                {
                    username = a.getUsername();
                    sign.Content = "查看账户";
                    welusername.Text = "欢迎!" + username;
                    welusername.Visibility = Visibility.Visible;
                    isSigned = true;
                }
            }
            else
            {
                AccountWindow a = new AccountWindow();
                a.username = username;
                a.ShowDialog();
            }
        }
    }
}
