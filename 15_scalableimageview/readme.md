# GestureDetector.OnGestureListener  onScroll() 方法详解

##  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)





1. distanceX 和distanceY代表的含义

   * 从官方解释来看

   * `distanceX` 和 `distanceY` 并不是 `e1` 和 `e2` 之间的总位移，而是 **自上次回调以来手指滑动的距离**。‘

   * 假设 `onScroll()` 被 **多次调用**，则：

     - **`distanceX = 前一次回调的 e2.getX() - 当前 e2.getX()`**
     - **`distanceY = 前一次回调的 e2.getY() - 当前 e2.getY()`**

     这意味着：

     - **手指向右滑** (`e2.getX()` **增大**) → `distanceX` **为负**

     - **手指向左滑** (`e2.getX()` **减小**) → `distanceX` **为正**

     - **手指向下滑** (`e2.getY()` **增大**) → `distanceY` **为负**

     - **手指向上滑** (`e2.getY()` **减小**) → `distanceY` **为正**

       

2. `distanceX` 和 `distanceY` 的计算方式

   * 假设onScroll()被连续调用

   第一帧: e1(100, 200) → e2(120, 220)  → distanceX = -20, distanceY = -20
   第二帧: e1(100, 200) → e2(150, 250)  → distanceX = -30, distanceY = -30

   所以，`distanceX` **表示的是手指在 `X` 轴上的相对移动量，而不是 `e1` 和 `e2` 之间的总距离**。

   

3. 为什么 `distanceX` 是反向的？

   * 在onScroll () 里:

     ```  java
     @Override
     public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
         offsetX -= distanceX;
         offsetY -= distanceY;
         invalidate();
         return true;
     }  
     ```

     * 由于 `distanceX = 前一次 `e2.getX()`- 当前`e2.getX()`，
        所以：
       - **手指右滑 (`distanceX < 0`)，offsetX -= distanceX，相当于 `offsetX += |distanceX|`，使图片向右移动**
       - **手指左滑 (`distanceX > 0`)，offsetX -= distanceX，使图片向左移动**
       - **手指下滑 (`distanceY < 0`)，offsetY -= distanceY，使图片向下移动**
       - **手指上滑 (`distanceY > 0`)，offsetY -= distanceY，使图片向上移动**
