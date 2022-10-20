<template>
  <q-layout view="lHh Lpr lFf">

    <q-page-container>
      <div class="q-pa-lg">
        <router-view />
      </div>
    </q-page-container>

    <q-footer class="bg-white" bordered>
      <q-toolbar>
        <span class="text-grey">Created By: Gustavo Chinaglia</span>
          <q-space />
        <span class="text-grey">Version: {{ currentVersion }}</span>
      </q-toolbar>
    </q-footer>
  </q-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'MainLayout',
  data() {
    return {
      currentVersion: process.env.currentVersion //TODO: Add this to process.env would be the best option
    }
  },

  components: {
  },

});
</script>
<style lang="scss">
</style>
