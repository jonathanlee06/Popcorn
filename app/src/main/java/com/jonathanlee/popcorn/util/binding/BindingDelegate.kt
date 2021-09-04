package com.jonathanlee.popcorn.util.binding

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Activity.viewBinding() = ActivityBinding(T::class.java)
inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentBinding(T::class.java, this)

class ActivityBinding<T : ViewBinding>(private val bindingClass: Class<T>) :
    ReadOnlyProperty<Activity, T> {

    /**
     * initiate variable for binding view
     */
    private var binding: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        binding?.let { return it }

        /** Inflate View Class */
        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)

        /** Bind Layout */
        val invokeLayout = inflateMethod.invoke(null, thisRef.layoutInflater) as T

        /** Set the content view */
        thisRef.setContentView(invokeLayout.root)

        return invokeLayout.also { this.binding = it }
    }

}

class FragmentBinding<T : ViewBinding>(
    bindingClass: Class<T>,
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    /**
     * initiate variable for binding view
     */
    private var binding: T? = null

    /**
     * get the bind method from View class
     */
    private val bindMethod = bindingClass.getMethod("bind", View::class.java)

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }

        /**
         * Adding observer to the fragment lifecycle
         */
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            /**
                             * Clear the binding when Fragment lifecycle called the onDestroy
                             */
                            binding = null
                        }
                    })
                }
            }
        })


        /**
         * Checking the fragment lifecycle
         */
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }


        /**
         * Bind layout
         */
        val invoke = bindMethod.invoke(null, thisRef.requireView()) as T

        return invoke.also { this.binding = it }
    }
}