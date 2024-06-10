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
    public partial class Nhân_viên : Form
    {
        public Nhân_viên()
        {
            InitializeComponent();
        }

        private void label7_Click(object sender, EventArgs e)
        {

        }

        private void button9_Click(object sender, EventArgs e)
        {
            Nhà_cung_cấp f = new Nhà_cung_cấp();
            f.Show();
            this.Hide();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Form1 f = new Form1();
            f.Show();
            this.Hide();
        }

        private void button7_Click(object sender, EventArgs e)
        {
            Calam f = new Calam();
            f.Show();
            this.Hide();
        }

        private void label9_Click(object sender, EventArgs e)
        {

        }

        private void Nhân_viên_Load(object sender, EventArgs e)
        {

        }

        private void button3_Click(object sender, EventArgs e)
        {
            Hàng_hóa f = new Hàng_hóa();
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

        private void button14_Click(object sender, EventArgs e)
        {
            Công_việc f = new Công_việc();
            f.Show();
            this.Hide();
        }
    }
}
