package kz.example.statesharedflows.ui


import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 4/28/21
 */

/**
 * Send event to observer if did not get the current value.
 * This realization we can use for events like notifications and errors (as sample to show in toasts, snackbars, dialogs)
 * @see <a href="https://proandroiddev.com/livedata-with-single-events-2395dea972a8">original post</a>
 *
 * Details of this solution you can see in github library:
 * @see <a href="https://github.com/hadilq/LiveEvent">original github repo</a>
 */
class SingleEventLiveData<T> : MediatorLiveData<T>() {

    //region Vars
    private val observers = ArraySet<ObserverWrapper<in T>>()
    //endregion

    //region Overridden Methods
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        observers.find { it.observer === observer }?.let { _ -> // existing
            return
        }
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        observers.find { it.observer === observer }?.let { _ -> // existing
            return
        }
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observeForever(wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observer is ObserverWrapper && observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapper = iterator.next()
            if (wrapper.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapper)
                break
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }
    //endregion

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T?) {
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}