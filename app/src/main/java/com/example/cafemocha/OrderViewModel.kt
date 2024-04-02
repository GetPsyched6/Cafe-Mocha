package com.example.cafemocha

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//! Logcat TAG
const val TAG: String = "MochaCafe"

class OrderViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>(listOf())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    fun updateCart(foodItem: FoodItem, changeInQuantity: Int) {
        val currentCartItems = cartItems.value ?: mutableListOf()
        var isItemInCart = false

        val updatedCartItems = currentCartItems.mapNotNull {
            if (it.foodItem == foodItem) {
                isItemInCart = true
//                Log.i(TAG, (it.quantity + changeInQuantity).toString())
                if (it.quantity + changeInQuantity > 0) {
                    it.copy(quantity = (it.quantity + changeInQuantity).coerceAtLeast(0))
                }
                else {
                    null
                }
            } else {
                it
            }
        }.toMutableList()
        //! Adding new item in cart as it is not found in cartItems
        if (!isItemInCart && changeInQuantity > 0) {
            updatedCartItems.add(CartItem(foodItem, changeInQuantity))
        }
        Log.d(TAG, updatedCartItems.toString())
        _cartItems.value = updatedCartItems
    }

}
