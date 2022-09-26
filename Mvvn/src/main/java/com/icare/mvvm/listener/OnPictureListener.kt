package com.icare.mvvm.listener

import com.luck.picture.lib.entity.LocalMedia

/**
 * +----------------------------------------------------------------------
 * | zaihukeji [ WE CAN DO IT MORE SIMPLE]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016-2021 http://icarexm.com/ All rights reserved.
 * +----------------------------------------------------------------------
 * | Author: Mr.He    <email：450951286@qq.com>
 * +----------------------------------------------------------------------
 *
 * @description:     类作用描述
 * @createDate:     2022/8/25
 * @updateUser:     更新者：Mr.He
 * @updateDate:     2022/8/25
 * @updateRemark:   更新说明：
 * @version:        1.0
 **/
interface OnPictureListener {
    fun onResult(selectList:ArrayList<LocalMedia>,type:Int)
}