import Vue from 'vue';
import VueRouter from "vue-router";
import LiveSample from "@/components/LiveSample";
import VodSample from "@/components/VodSample";
import VueVodPlayer from "@/components/VueVodPlayer";
import AppMenu from "@/components/AppMenu";

Vue.use(VueRouter);
export const router = new VueRouter({
    mode: 'history',
    routes:
        [
            {
                path: '/',
                component: AppMenu,
                children: [
                    {
                        path: '/live',
                        component: LiveSample

                    },
                    {
                        path: '/vod',
                        component: VodSample

                    }

                ]

            },
            {
                path: '/vodplayer/:mckey',
                component: VueVodPlayer,
            }

        ]

})
