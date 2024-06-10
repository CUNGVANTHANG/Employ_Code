using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace He_thong_kinh_doanh_Pizza
{
    public partial class Hàng_hóa : Form
    {
        public Hàng_hóa()
        {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Form1 f = new Form1();
            f.Show();
            this.Hide();
        }

        private void Hàng_hóa_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void button6_Click(object sender, EventArgs e)
        {
            Nhân_viên f = new Nhân_viên();
            f.Show();
            this.Hide();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            Thongke f = new Thongke();
            f.Show();
            this.Hide();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            Thống_kê f = new Thống_kê();
            f.Show();
            this.Hide();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Calam f = new Calam();
            f.Show();
            this.Hide();
        }

        private void button14_Click(object sender, EventArgs e)
        {
            Công_việc f = new Công_việc();
            f.Show();
            this.Hide();
        }

        private void button15_Click(object sender, EventArgs e)
        {
            Nhà_cung_cấp f = new Nhà_cung_cấp();
            f.Show();
            this.Hide();
        }

        private void button9_Click(object sender, EventArgs e)
        {

        }
    }
}
