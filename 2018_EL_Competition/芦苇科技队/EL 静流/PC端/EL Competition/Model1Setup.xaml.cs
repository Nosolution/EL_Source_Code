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
using System.Reflection;
using System.Windows.Resources;
using System.Diagnostics;

namespace EL_Competition
{
    /// <summary>
    /// Model1Setup.xaml 的交互逻辑
    /// </summary>
    public partial class Model1Setup : Window
    {
        public bool issigned =false;
        public string username;
        public bool isfirstclick = true;
        bool isinSmode = false;
        public Model1Setup()
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
            if (File.Exists(SaveLoc + @"\filebansave.ini"))
            {
                using (StreamReader filebansave = new StreamReader(SaveLoc + @"\filebansave.ini"))
                {
                    while (filebansave.Peek() >= 0)
                    {
                        string line = filebansave.ReadLine();
                        forbiddenfile.Add(line);
                        Button btntt = new Button() { BorderBrush = Brushes.Purple, Height = 20, HorizontalAlignment = HorizontalAlignment.Left, VerticalAlignment = VerticalAlignment.Top, Content = "删除对于该软件的禁止", Background = Brushes.Transparent };
                        TextBlock textt = new TextBlock() { Foreground = Brushes.Purple, Height = 20, HorizontalAlignment = HorizontalAlignment.Left, VerticalAlignment = VerticalAlignment.Top, Text = line };
                        btn.Add(btntt);
                        tex.Add(textt);
                        tem.Children.Add(textt);
                        tem.Children.Add(btntt);
                        fileviewer.Content = tem;
                        btntt.Click += Model1Setup_Click;
                    }
                }
            }//添加保存的禁止文件
            if (File.Exists(SaveLoc + @"\setting.ini"))
            {
                StartButton.Content = "更改设置";
                using (StreamReader setting = new StreamReader(SaveLoc + @"\setting.ini"))
                {
                    List<string> settings = new List<string>();
                    while (setting.Peek() >= 0)
                    {
                        settings.Add(setting.ReadLine());
                    }
                    if (settings[0] == "Strong")
                    {
                        isOpenSmode.IsChecked = true;
                        isOpenSmode.IsEnabled = false;
                        hour.IsEnabled = false;
                        minute.IsEnabled = false;
                        second.IsEnabled = false;
                        datehour.IsEnabled = false;
                        dateminte.IsEnabled = false;
                        datepicker.IsEnabled = false;
                        datesecond.IsEnabled = false;
                        timeset1.IsEnabled = false;
                        timeset2.IsEnabled = false;
                        isinSmode = true;
                        StartButton.Content = "强力模式不能更改设置";
                        StartButton.Width = 300;
                        foreach (Button i in btn)
                        {
                            i.IsEnabled = false;
                        }
                    }

                    if (settings[1] == "1")
                    {
                        timeset1.IsChecked = true;
                        String[] a = (Convert.ToDateTime(settings[2]) - DateTime.Now).ToString().Split(':');
                        hour.SelectedIndex = int.Parse(a[0]);
                        minute.SelectedIndex = int.Parse(a[1]);
                        second.SelectedIndex = int.Parse(a[2].Split('.')[0]);
                    }
                    else
                    {
                        String[] a = settings[2].Split(' ')[1].Split(':');
                        timeset2.IsChecked = true;
                        datehour.SelectedIndex = int.Parse(a[0]);
                        dateminte.SelectedIndex = int.Parse(a[1]);
                        datesecond.SelectedIndex = int.Parse(a[2]);
                        datepicker.SelectedDate = Convert.ToDateTime(settings[2]);
                    }


                }//加载设置

            }
        }

        public static string SaveLoc = @"C:\ProgramData\JingLiu", forbidexe = @"C:\ProgramData\JingLiu\forbidden.exe";
        private void StartButton_Click(object sender, RoutedEventArgs e)
        {
            if(timeset1.IsChecked == true|| timeset2.IsChecked == true)
            {
                if (isfirstclick)
                {
                    if (!Directory.Exists(SaveLoc))
                    {
                        Directory.CreateDirectory(SaveLoc);
                    }
                    if (Directory.Exists(SaveLoc))
                    {
                        using (StreamWriter filebansave = new StreamWriter(SaveLoc + @"\filebansave.ini"))
                        {
                            foreach (string i in forbiddenfile)
                            {
                                filebansave.WriteLine(i);
                            }
                        }//写入禁止文件路径
                        using (StreamWriter setting = new StreamWriter(SaveLoc + @"\setting.ini"))
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
                                if((A-DateTime.Now)<(Convert.ToDateTime("0:0:30")-Convert.ToDateTime("0:0:0")))
                                {
                                    MessageBox.Show("专注时长不得低于30s");
                                    setting.Close();
                                    File.Delete(SaveLoc + @"\setting.ini");
                                    return;
                                }
                                setting.WriteLine("1"+"\r\n"+A.ToString());
                            }
                            else
                            {
                                string str1 = datehour.SelectedIndex.ToString() + ":" + dateminte.SelectedIndex.ToString() + ":" + datesecond.SelectedIndex.ToString();
                                DateTime? A = (datepicker.SelectedDate + (Convert.ToDateTime(str1) - Convert.ToDateTime("0:0:0")));
                                if ((A - DateTime.Now) < (Convert.ToDateTime("0:0:30") - Convert.ToDateTime("0:0:0")))
                                {
                                    MessageBox.Show("专注时长不得低于30s");
                                    setting.Close();
                                    File.Delete(SaveLoc + @"\setting.ini");
                                    return;
                                }
                                setting.WriteLine("2" + "\r\n" + A.ToString());
                            }
                        }//写入设置
                        using (StreamWriter filebaning = new StreamWriter(SaveLoc + @"\filebaning.ini"))
                        {
                            foreach (string i in forbiddenfile)
                            {
                                String[] a = i.Split('\\');
                                String filename = a[a.Length - 1];
                                if (File.Exists(i)) File.Copy(i, SaveLoc + @"\" + filename + ".ini", true);
                                if (!File.Exists(forbidexe))
                                {
                                    MessageBox.Show("EORRO:必要文件丢失:pack://application:,,,/Resources/forbidden.exe");
                                    File.Delete(SaveLoc + "/setting.ini");
                                    File.Copy("pack://application:,,,/Resources/forbidden.exe", forbidexe, true);
                                }
                                if (File.Exists(SaveLoc + @"\" + filename + ".ini"))
                                {
                                    File.Delete(i);
                                    File.Copy(forbidexe, i, true);
                                }
                                filebaning.WriteLine(i);
                            }
                        }
                    }
                    else
                    {
                        MessageBox.Show("错误：创建保存目录失败");
                        Close();
                    }
                    Model1 model1 = new Model1();
                    model1.Show();
                    model1.issigned = issigned;
                    model1.username = username;
                    this.Close();
                }
                else
                {
                    using (StreamWriter setting = new StreamWriter(SaveLoc + @"\setting.ini"))
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
                            setting.WriteLine("1" + "\r\n" + A.ToString());
                        }
                        else
                        {
                            string str1 = datehour.SelectedIndex.ToString() + ":" + dateminte.SelectedIndex.ToString() + ":" + datesecond.SelectedIndex.ToString();
                            DateTime? A = (datepicker.SelectedDate + (Convert.ToDateTime(str1) - Convert.ToDateTime("0:0:0")));
                            setting.WriteLine("2" + "\r\n" + A.ToString());
                        }
                    }//写入设置

                    this.Close();
                }
            }
            else
            {
                MessageBox.Show("请先设置专注时间");
            }
        }
        
        private void RadioButton_Checked(object sender, RoutedEventArgs e)
        {
            if (!isinSmode)
            {
                hour.IsEnabled = true;
                minute.IsEnabled = true;
                second.IsEnabled = true;
                datehour.IsEnabled = false;
                dateminte.IsEnabled = false;
                datepicker.IsEnabled = false;
                datesecond.IsEnabled = false;
            }
        }

        private void RadioButton_Checked_1(object sender, RoutedEventArgs e)
        {
            if (!isinSmode)
            {
                datehour.IsEnabled = true;
                dateminte.IsEnabled = true;
                datepicker.IsEnabled = true;
                datesecond.IsEnabled = true;
                hour.IsEnabled = false;
                minute.IsEnabled = false;
                second.IsEnabled = false;
            }
        }

        
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.OpenFileDialog openFileDialog = new Microsoft.Win32.OpenFileDialog();
            openFileDialog.Title = "选择文件";
            openFileDialog.Filter = "所有文件(*.*)|*.*";
            openFileDialog.FileName = "选择需要禁止的程序.";
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
        List<string> forbiddenfile = new List<string>();
        StackPanel tem = new StackPanel();
        List<Button> btn = new List<Button>();
        List<TextBlock> tex = new List<TextBlock>();
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            if (!forbiddenfile.Contains(fileloc.Text))
            {
                if (File.Exists(fileloc.Text))
                {
                    
                    if (true)
                    {
                        forbiddenfile.Add(fileloc.Text);
                        Button btntt = new Button() { BorderBrush = Brushes.Purple, Height = 20, HorizontalAlignment = HorizontalAlignment.Left, VerticalAlignment = VerticalAlignment.Top, Content = "删除对于该软件的禁止", Background = Brushes.Transparent };
                        TextBlock textt = new TextBlock() { Foreground = Brushes.Purple, Height = 20, HorizontalAlignment = HorizontalAlignment.Left, VerticalAlignment = VerticalAlignment.Top, Text = fileloc.Text };
                        btn.Add(btntt);
                        tex.Add(textt);
                        tem.Children.Add(textt);
                        tem.Children.Add(btntt);
                        fileviewer.Content = tem;
                        btntt.Click += Model1Setup_Click;
                        fileloc.Text = "添加下一个文件";
                    }
                }
                else
                {
                    MessageBox.Show("文件不存在");
                }
            }
            else
            {
                MessageBox.Show("该程序已添加");
            }
        }

        private void Model1Setup_Click(object sender, RoutedEventArgs e)
        {
            Button btntem = (Button)sender;
            tem.Children.Remove(btntem);
            tem.Children.Remove(tex[btn.IndexOf(btntem)]);
            forbiddenfile.Remove(tex[btn.IndexOf(btntem)].Text);
            tex.Remove(tex[btn.IndexOf(btntem)]);
            btn.Remove(btntem);
            
            //移除对应按钮控件
        }

        private void fileloc_TextChanged(object sender, TextChangedEventArgs e)
        {
            if(fileloc.Text != "在这里添加文件"&& fileloc.Text != "添加下一个文件"&& fileloc.Text != "专注途中不可以更改禁止的程序哦") addnew.IsEnabled = true;
            if(fileloc.Text == "添加下一个文件") addnew.IsEnabled = false;
            if (!isfirstclick)
            {
                addnew.IsEnabled = false;
                fileloc.IsEnabled = false;
                lookup.IsEnabled = false;
                fileloc.Text = "专注途中不可以更改禁止的程序哦";
                foreach(Button i in btn)
                {
                    i.IsEnabled = false;
                }
            }
        }

        private void fileloc_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter&&addnew.IsEnabled)
            {
                addnew.RaiseEvent(new RoutedEventArgs(Button.ClickEvent));
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
