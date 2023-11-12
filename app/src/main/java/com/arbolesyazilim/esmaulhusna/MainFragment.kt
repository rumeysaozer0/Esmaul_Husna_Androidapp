package com.arbolesyazilim.esmaulhusna



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment(), NameAdapter.ItemClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView'ı bul ve ayarla
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Constants sınıfından verileri al
        val nameList = Constants.getNames()

        // Adapter'ı oluştur ve RecyclerView'a set et
        val adapter = NameAdapter(nameList, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(name: Name) {
        val action = MainFragmentDirections.actionMainFragmentToSecondFragment(name)
        findNavController().navigate(action)
    }


}

