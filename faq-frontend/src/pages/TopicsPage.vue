<template>
  <div>
    <q-input rounded outlined dense>
      <template v-slot:prepend>
        <q-icon name="search" />
      </template>
      <template v-slot:append>
        <q-btn icon="send" round flat />
      </template>
    </q-input>
  </div>
  <div class="q-pt-md"></div>
  <q-page-sticky position="bottom-right" :offset="[18, 18]">
    <q-fab color="primary"></q-fab>
  </q-page-sticky>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  title: 'TopicsPage',
});
</script>
