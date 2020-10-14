<template>
  <div>
    <div>Order Number: {{orderInfo.orderNo}}</div>
    <div>Order Price: {{orderInfo.price}}</div>
  </div>
</template>
<script>
import orderApi from "@/utils/api/order";
export default {
  deactivated() {
    this.$destroy();
  },
  name: "",
  created() {
    this.getOrderByOrderNo();
  },
  data() {
    return {
      orderInfo: "",
    };
  },
  methods: {
    async getOrderByOrderNo() {
      const data = await orderApi.getOrderByOrderNo(this.$route.query.orderNo);
      console.log(data);
      this.orderInfo = data.data.data;
    },
  },
};
</script>
<style lang="scss" scoped>
</style>