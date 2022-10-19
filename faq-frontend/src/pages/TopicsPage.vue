<template>
  <q-page-container>
    <q-page>
      <div>
        <q-input rounded outlined>
          <template v-slot:prepend>
            <q-icon name="search" />
          </template>
          <template v-slot:append>
            <q-btn round flat :rippe="false" icon="send" />
          </template>
        </q-input>
      </div>
      <div class="q-pt-lg">
        <q-list v-for="topic in topics" :key="topic.id">
          <TopicBasicComponent :title="topic.title" :answer="topic.answer" />
        </q-list>
      </div>
      <q-page-sticky position="bottom-right" :offset="[18, 18]">
        <q-fab color="primary"></q-fab>
      </q-page-sticky>
    </q-page>
  </q-page-container>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { api } from 'boot/axios';
import { TopicModel } from 'src/model/TopicModel';
import TopicBasicComponent from 'components/TopicBasicComponent.vue';

export default defineComponent({
  components: { TopicBasicComponent },
  title: 'TopicsPage',
  data() {
    return {
      topics: [] as TopicModel[],
    };
  },
  methods: {
    findAllTopics() {
      api
        .get('/v1/topic')
        .then((response) => {
          this.topics = response.data;
        })
        .catch((e) => {
          console.error('Error: ', e);
        });
    },
  },
  mounted() {
    this.findAllTopics();
  },
});
</script>
