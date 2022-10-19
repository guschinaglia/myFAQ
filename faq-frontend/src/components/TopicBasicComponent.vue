<template>
  <q-expansion-item popup header-class="bg-blue-1" :label="title">
    <q-card>
      <q-card-section>
        {{ answer }}
      </q-card-section>
    </q-card>
  </q-expansion-item>
</template>

<script>
import { defineComponent } from 'vue';
export default defineComponent({
  name: 'TopicBasicComponent',
  props: {
    title: String,
    answer: String,
  }
});
</script>

<style scoped>

</style>
