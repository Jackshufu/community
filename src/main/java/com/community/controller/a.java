/*
package com.community.controller;

*/
/**
 * Created by 舒先亮 on 2019/8/28.
 *//*

public class a {
    @Override
    public PageInfo<DeviceReturnWebVO> queryDeviceByCondition(ConditionForQueryDeviceInfoBO conditionForQueryDeviceInfoBO) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "=====根据条件获取设备概览信息====:" + JSON.toJSONString(conditionForQueryDeviceInfoBO));
        List<DeviceReturnWebVO> deviceReturnWebs = new ArrayList<>();
        */
/**
         * 获取设备概要信息
         *//*

        long startTime = System.currentTimeMillis();
        PageHelper.startPage(conditionForQueryDeviceInfoBO.getPageNumber(), conditionForQueryDeviceInfoBO.getPageSize());
        List<DeviceReturnWebVO> page = iDeviceManageDao.queryDeviceByCondition(conditionForQueryDeviceInfoBO);
        PageInfo<DeviceReturnWebVO> pageInfo = new PageInfo<>(page);
        long chapter1Time = System.currentTimeMillis();
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "===根据条件获取设备概览信息===耗时:" +
                (chapter1Time - startTime) + "ms===List<DeviceReturnWebVO>:" + JSON.toJSONString(page));
        if (page != null && page.size() > 0) {
            Map<String, Object> params = new HashMap<>();
            params.put("status", DataStatusEnum.DATA_STATUS_ENABLE.getStatusCode());
            for (DeviceReturnWebVO deviceReturnWebVO : page) {
                String deviceType = deviceReturnWebVO.getDeviceType();
                String uId = deviceReturnWebVO.getuId();
                deviceReturnWebVO.setDeviceTypeName(DeviceTypeInfoEnum.getDescription(deviceType));

                //查询二级设备信息以及分组信息
                params.put("uId", uId);
                deviceReturnWebVO.setSecondDeviceReturnWebs(iSecondaryDeviceInfoDao.getSecondByUId(params));

                deviceReturnWebs.add(deviceReturnWebVO);
            }
            pageInfo.setList(deviceReturnWebs);
        }
        long chapter2Time = System.currentTimeMillis();
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "===根据条件获取设备概览信息==子级设备信息===耗时:" +
                (chapter2Time - chapter1Time) + "ms ===content:" + JSON.toJSONString(deviceReturnWebs));
        return pageInfo;
    }

}
*/

/*<div id="select-tag" style="display:none">
<ul id="myTab" class="nav nav-tabs" aria-labelledby="dropdownMenu1">
<li th:each="selectCategory: ${tags}">
<a th:href="${'#'+selectCategory.CategoryName}" data-toggle="tab"
        th:text="${selectCategory.CategoryName}">
</a>
</li>
</ul>
<div id="myTabContent" class="tab-content" >
<div class="tab-pane fade in active" th:each="selectCategory: ${tags}"
        >
<a class="publish-tag" onclick="selectTags(this)" th:data-tag="${selectTag}"
        th:id="${selectCategory.CategoryName}" th:each="selectTag:${selectCategory.tags}"
        th:text="${selectTag}"></a>
</div>
</div>
</div>*/

