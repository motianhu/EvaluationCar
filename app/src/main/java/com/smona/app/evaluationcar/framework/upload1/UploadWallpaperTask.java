package com.smona.app.evaluationcar.framework.upload1;

import android.content.Context;
import android.text.TextUtils;

import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.EventBus;
import java.util.LinkedHashMap;

import java.util.Map;

/**
 * Created by ligang on 10/12/16.
 */

public class UploadWallpaperTask {
    private static final String TAG = "UploadWallpaperTask";

    public UploadWallpaperTask() {
    }


    public void startUploadWallpaper(Context context, String userId, String publishId) {
        CarLog.d(TAG, "startUploadWallpaper userId = " + userId + " publishId = " + publishId);
        ArrayList<ItemInfo> infos = getUploadWallpaperDataByPublishId(context, publishId);
        CarLog.d(TAG, "startUploadWallpaper infos = " + infos);
        if (infos == null || infos.size() != 1) {
            return;
        }
        uploadWallpaper(context, userId, publishId, infos.get(0));
    }

    private ArrayList<ItemInfo> getUploadWallpaperDataByPublishId(Context context, String publishId) {
        UploadPublishSet itemSet = new UploadPublishSet();
        itemSet.loadFromDBByPublishId(context, publishId);
        return itemSet.getDataSet();
    }

    private int deleteUploadWallpaperDataByPublishId(Context context, String publishId) {
        UploadPublishSet itemSet = new UploadPublishSet();
        return itemSet.deleteByPublishId(context, publishId);
    }


    private void uploadWallpaper(Context context, String userId, String aid, ItemInfo itemInfo) {
        CarLog.d(TAG, "startUploadWallpaper userId = " + userId + " aid = " + aid + " itemInfo = " + itemInfo);
        if (itemInfo == null || !(itemInfo instanceof UserPublishItem)) {
            return;
        }
        UserPublishItem userPublishItem = (UserPublishItem) itemInfo;
        List<PublishImageItem> imageItems = userPublishItem.getImages();
        EventBusUploadProgress progress = new EventBusUploadProgress();
        progress.setId(aid);
        if (imageItems == null || imageItems.size() == 0) {
            return;
        }
        updatePublishStatus(context, aid, UserPublishItem.STATUS_UPLOADING);
        progress.setAllCount(imageItems.size() + 1);
        int finishCount = 0;
        for (PublishImageItem publishImageItem : imageItems) {
            CarLog.d(TAG, "for publishImageItem = " + publishImageItem);
            if (!TextUtils.isEmpty(publishImageItem.getSourceImage())) {
                finishCount++;
                progress.setSucessCount(finishCount);
                EventBus.getDefault().post(progress);
                continue;
            }
            String result = uploadWallpaper(userId, aid, publishImageItem, progress);
            if (UploadUtil.FILE_NOT_EXIST_ERROR.equals(result)) {
                if (imageItems.size() == 1) {
                    progress.setSuccessStatus(false);
                    updatePublishStatus(context, aid, UserPublishItem.STATUS_UPLOAD_FAILED);
                    EventBus.getDefault().post(progress);
                    return;
                } else {
                    finishCount++;
                    progress.setSucessCount(finishCount);
                    EventBus.getDefault().post(progress);
                    continue;
                }
            }
            if (TextUtils.isEmpty(result)) {
                progress.setSuccessStatus(false);
                updatePublishStatus(context, aid, UserPublishItem.STATUS_UPLOAD_FAILED);
                EventBus.getDefault().post(progress);
                return;
            }
            Class<?> clazz = ServerInterfaceType.UPLOAD_SINGLE_WALPPAPER.getDataClass();
            Object object = JsonParseFactory.parseJson(result, clazz,
                    ServerInterfaceType.UPLOAD_SINGLE_WALPPAPER.getParse());
            if (object == null || !(object instanceof UploadSingleWallpaperResultBean)) {
                progress.setSuccessStatus(false);
                updatePublishStatus(context, aid, UserPublishItem.STATUS_UPLOAD_FAILED);
                EventBus.getDefault().post(progress);
                return;
            }
            UploadSingleWallpaperResultBean itemBean = (UploadSingleWallpaperResultBean) object;
            if (itemBean.getData() == null || TextUtils.isEmpty(itemBean.getData().getUploadResult())) {
                progress.setSuccessStatus(false);
                updatePublishStatus(context, aid, UserPublishItem.STATUS_UPLOAD_FAILED);
                EventBus.getDefault().post(progress);
                return;
            }
            updateUploadUrl(context, aid, publishImageItem.getId(), itemBean.getData().getUploadResult());
            publishImageItem.setSourceImage(itemBean.getData().getUploadResult());
            finishCount++;
            progress.setSucessCount(finishCount);
            progress.setUploadByteCount(0);
            EventBus.getDefault().post(progress);
        }
        uploadPublishSuceess(context, userId, aid, progress);
    }


    private void updateUploadUrl(Context context, String publishId, String imageId, String url) {
        UploadPublishSet itemSet = new UploadPublishSet();
        itemSet.updateItemPublisImageServerUrl(context, publishId, imageId, url);
    }

    private String uploadWallpaper(String userId, String aid, PublishImageItem publishImageItem, EventBusUploadProgress progress) {
        CarLog.d(TAG, "uploadWallpaper userId = " + userId + " aid = " + aid + " publishImageItem = " + publishImageItem);
        String url = WallpaperConstants
                .getUrlPath(ForgeData.getInterfaceHome());
        url = url + ServerInterfaceType.UPLOAD_SINGLE_WALPPAPER.getUrl() + "?aid=" + aid + "&userId=" + userId;
        String fileName = aid + "_" + publishImageItem.getId() + ".png";
        return UploadUtil.uploadFile(url, fileName, publishImageItem.getThumbImage(), progress);
    }

    private void updatePublishStatus(Context context, String publishId, int status) {
        UploadPublishSet itemSet = new UploadPublishSet();
        itemSet.updateUploadStatatus(context, publishId, status);
    }

    private void uploadPublishSuceess(final Context context, String userId, final String aid, final EventBusUploadProgress progress) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("userId", userId);
        params.put("aId", aid);
        DataCacheControl.loadDataByInterfaceTypeAndPageNo(
                ServerInterfaceType.NEW_BANNER_LIST,
                DataCacheControl.PAGE_START_INDEX, new DataCacheControl.CacheDataRequestListener() {
                    @Override
                    public void onSuccess(int type, Object object) {
                        deleteUploadWallpaperDataByPublishId(context, aid);
                        progress.setSucessCount(progress.getAllCount());
                        updatePublishStatus(context, aid, UserPublishItem.STATUS_UPLOAD_OK);
                        EventBus.getDefault().post(progress);
                    }

                    @Override
                    public void onFailure(int type, String error) {
                        progress.setSuccessStatus(false);
                        updatePublishStatus(context, aid, UserPublishItem.STATUS_UPLOAD_FAILED);
                        EventBus.getDefault().post(progress);
                    }
                }, params);
    }

}
