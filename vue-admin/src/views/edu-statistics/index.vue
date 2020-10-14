<template>
  <div>
    <div class="block">
      <el-date-picker
        v-model="date"
        align="right"
        type="date"
        placeholder="选择日期"
        :picker-options="pickerOptions"
        value-format="yyyy-MM-dd"
      ></el-date-picker>
    </div>
    <div @click="generateClick" style="margin-left:50px">
      <button>Generate Chart</button>
    </div>
    <hr />
    <div class="block">
      <el-dropdown @command="handleCommand">
        <el-button style="width:200px" type="primary">
          {{selectText}}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="register_num">Register Num</el-dropdown-item>
          <el-dropdown-item command="id">Id</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-date-picker
        style="margin-left:30px"
        v-model="dateRange"
        type="daterange"
        align="right"
        unlink-panels
        range-separator="To"
        start-placeholder="Start Date"
        end-placeholder="End Date"
        value-format="yyyy-MM-dd"
        :picker-options="pickerOptions"
      ></el-date-picker>
      <div @click="searchClick" style="margin-left:50px">
        <button>Search</button>
      </div>
      <div style="height:400px;width:800px" id="chart"></div>
    </div>
  </div>
</template>
<script>
import api from "@/api/sta";
import { log } from "util";
import echarts from "echarts";
export default {
  name: "",
  mounted() {
    let myChart = echarts.init(document.getElementById("chart"));
    let option = {
      title: {
        text: "City Weather",

        left: "center",
      },
      tooltip: {
        trigger: "item",
        formatter: "{a} <br/>{b} : {c} ({d}%)",
      },
      legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: "center",
        data: ["City1", "City2", "City3", "City4", "City5"],
      },
      series: [
        {
          type: "pie",
          radius: "65%",
          center: ["50%", "50%"],
          selectedMode: "single",
          data: [
            { value: 1548, name: "City1" },
            { value: 535, name: "City2" },
            { value: 510, name: "City3" },
            { value: 634, name: "City4" },
            { value: 735, name: "City5" },
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
        },
      ],
    };
    myChart.setOption(option);
  },
  data() {
    return {
      date: "",
      selectText: "Select Factor",
      dateRange: "",
      chartYData: [],
      chartYData: [],
      condition: {
        factor: null,
        min: null,
        max: null,
      },
      pickerOptions: {
        // disabledDate(time) {
        //   return time.getTime() > Date.now();
        // },
        shortcuts: [
          {
            text: "Today",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "Yesterday",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "A week ago",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
    };
  },
  methods: {
    async generateClick() {
      const data = await api.getRegisterCountByDate(this.date);
      console.log(data);
    },
    handleCommand(command) {
      this.condition.factor = command;
      switch (command) {
        case "register_num":
          return (this.selectText = "Register Num");
        case "id":
          return (this.selectText = "Id");
      }
    },
    async searchClick() {
      this.condition.min = this.dateRange[0];
      this.condition.max = this.dateRange[1];
      const data = await api.getStatisticsForChart(this.condition);
      // console.log(data);
      this.chartYData = data.data.map((i) => {
        return i[this.condition.factor];
      });
      this.chartXData = data.data.map((i) => {
        return i.calculated_date;
      });
      let myChart = echarts.init(document.getElementById("chart"));
      let option = {
        color: ["#f44"],
        title: { text: "sta" },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        xAxis: [
          {
            type: "category",
            data: this.chartXData,
            axisTick: {
              alignWithLabel: true,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
          },
        ],
        series: [
          {
            name: this.condition.factor,
            type: "bar",
            barWidth: "30%",
            data: this.chartYData,
          },
        ],
      };
      myChart.setOption(option);
    },
  },
};
</script>
<style  scoped>
.block {
  margin: 50px;
}
</style>