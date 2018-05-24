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
using System.IO;

namespace EL_Competition
{
    /// <summary>
    /// Model2setup.xaml 的交互逻辑
    /// </summary>
    /// 
    public partial class Model2setup : Window
    {
        public bool issigned = false;
        public string username;
        string SaveLoc = Model1Setup.SaveLoc;
        bool isinSmode = false;
        public Model2setup()
        {
            InitializeComponent();
            for (int i = 0; i <= 100; i++)
            {
                hour.Items.Add(new ComboBoxItem() { Content = i.ToString() });
            }
            for (int i = 0; i <= 59; i++)
            {
                minute.Items.Add(new ComboBoxItem() { Content = i.ToString() });
                second.Items.Add(new ComboBoxItem() { Content = i.ToString() });
                dateminte.Items.Add(new ComboBoxItem() { Content = i.ToString() });
                datesecond.Items.Add(new ComboBoxItem() { Content = i.ToString() });
            }
            for (int i = 0; i <= 23; i++)
            {
                datehour.Items.Add(new ComboBoxItem() { Content = i.ToString() });
            }
            datepicker.DisplayDate = DateTime.Today;
            datepicker.DisplayDateStart = DateTime.Today;
            datepicker.SelectedDate = DateTime.Today;
            hour.SelectedIndex = 0;
            second.SelectedIndex = 0;
            minute.SelectedIndex = 0;
            datehour.SelectedIndex = 0;
            dateminte.SelectedIndex = 0;
            datesecond.SelectedIndex = 0;
            timeset1.IsChecked = true;
        }
        private void StartButton_Click(object sender, RoutedEventArgs e)
        {

            if (!Directory.Exists(SaveLoc))
            {
                Directory.CreateDirectory(SaveLoc);
            }
            if (Directory.Exists(SaveLoc))
            {
                using (StreamWriter setting = new StreamWriter(SaveLoc + @"\setting2.ini"))
                {
                    if (isOpenSmode.IsChecked == true)
                    {
                        setting.WriteLine("Strong");
                    }
                    else
                    {
                        setting.WriteLine("No");
                    }

                    if (timeset1.IsChecked == true)
                    {
                        string str1 = hour.SelectedIndex.ToString() + ":" + minute.SelectedIndex.ToString() + ":" + second.SelectedIndex.ToString();
                        DateTime A = DateTime.Now + (Convert.ToDateTime(str1) - Convert.ToDateTime("0:0:0"));
                        if ((A - DateTime.Now) < TimeSpan.FromSeconds(30))
                        {
                            MessageBox.Show("专注时长不得低于30s");
                            setting.Close();
                            File.Delete(SaveLoc + @"\setting2.ini");
                            return;
                        }
                        setting.WriteLine("1" + "\r\n" + A.ToString());
                    }
                    else if (timeset2.IsChecked == true)
                    {
                        string str1 = datehour.SelectedIndex.ToString() + ":" + dateminte.SelectedIndex.ToString() + ":" + datesecond.SelectedIndex.ToString();
                        DateTime? A = (datepicker.SelectedDate + (Convert.ToDateTime(str1) - Convert.ToDateTime("0:0:0")));
                        if ((A - DateTime.Now) < TimeSpan.FromSeconds(30))
                        {
                            MessageBox.Show("专注时长不得低于30s");
                            setting.Close();
                            File.Delete(SaveLoc + @"\setting.ini");
                            return;
                        }
                        setting.WriteLine("2" + "\r\n" + A.ToString());
                    }
                    else if (timeset3.IsChecked == true)
                    {
                        setting.WriteLine("3"+ "\r\n"+"00");
                    }

                    if (backImage!=null)
                    {
                        setting.WriteLine("1" + "\r\n" + backImage);
                    }
                    else
                    {
                        setting.WriteLine("0" + "\r\n" + backImage);
                    }
                }//写入设置
            }
            Model2 m = new Model2();
            m.issigned = issigned;
            m.username = username;
            m.Show();
            this.Close();
        }

        private void timeset1_Checked(object sender, RoutedEventArgs e)
        {
            hour.IsEnabled = true;
            minute.IsEnabled = true;
            second.IsEnabled = true;
            datehour.IsEnabled = false;
            dateminte.IsEnabled = false;
            datepicker.IsEnabled = false;
            datesecond.IsEnabled = false;
            isOpenSmode.IsEnabled = true;
        }

        private void timeset2_Checked(object sender, RoutedEventArgs e)
        {
            datehour.IsEnabled = true;
            dateminte.IsEnabled = true;
            datepicker.IsEnabled = true;
            datesecond.IsEnabled = true;
            hour.IsEnabled = false;
            minute.IsEnabled = false;
            second.IsEnabled = false;
            isOpenSmode.IsEnabled = true;
        }

        private void timeset3_Checked(object sender, RoutedEventArgs e)
        {
            hour.IsEnabled = false;
            minute.IsEnabled = false;
            second.IsEnabled = false;
            datehour.IsEnabled = false;
            dateminte.IsEnabled = false;
            datepicker.IsEnabled = false;
            datesecond.IsEnabled = false;
            isOpenSmode.IsChecked = false;
            isOpenSmode.IsEnabled = false;
        }
        

        private void fileloc_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter && addnew.IsEnabled)
            {
                addnew.RaiseEvent(new RoutedEventArgs(Button.ClickEvent));
            }
        }

        private void lookup_Click(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.OpenFileDialog openFileDialog = new Microsoft.Win32.OpenFileDialog();
            openFileDialog.Title = "选择背景";
            openFileDialog.Filter = "PNG图片|*.png|JPG图片|*.jpg|BMP图片|*.bmp|GIF图片|*.gif";
            openFileDialog.FileName = "选择需要的背景图片";
            openFileDialog.FilterIndex = 1;
            openFileDialog.ValidateNames = false;
            openFileDialog.CheckFileExists = false;
            openFileDialog.CheckPathExists = true;
            openFileDialog.Multiselect = false;//允许同时选择多个文件 
            bool? result = openFileDialog.ShowDialog();
            if (result != true)
            {
                return;
            }
            else
            {
                fileloc.Text = openFileDialog.FileName;
            }
        }

        String backImage;
        private void fileloc_TextChanged(object sender, TextChangedEventArgs e)
        {
            string a = fileloc.Text;
            if (a.Length >= 5)
            {
                if (a.Substring(a.Length-3)=="jpg"|| a.Substring(a.Length - 3) == "gif" || a.Substring(a.Length - 3) == "bmp" || a.Substring(a.Length - 3) == "png" )
                {
                    addnew.IsEnabled = true;
                }
                else
                {
                    addnew.IsEnabled = false;
                }
            }
        }

        private void addnew_Click(object sender, RoutedEventArgs e)
        {
            if (File.Exists(fileloc.Text))
            {
                preview.Source = new BitmapImage(new Uri(fileloc.Text, UriKind.Absolute));
                backImage = fileloc.Text;
            }
            else
            {
                MessageBox.Show("文件不存在");
            }
        }

        private void back_Click(object sender, RoutedEventArgs e)
        {
            MainWindow tem = new MainWindow();
            tem.isSigned = issigned;
            tem.username = username;
            tem.Show();
            this.Close();
        }
    }
}
