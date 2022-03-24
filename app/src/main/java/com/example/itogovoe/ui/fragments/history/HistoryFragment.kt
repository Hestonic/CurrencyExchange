package com.example.itogovoe.ui.fragments.history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.R
import com.example.itogovoe.databinding.FragmentHistoryBinding
import com.example.itogovoe.ui.model.History
import java.time.LocalDate

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HistoryAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // TODO: сделать RoomDB данных для History
        val date = LocalDate.parse("2018-12-12")
        val historyList: List<History> = listOf(
            History(
                date,
                "EUR",
                3.22,
                "RUB",
                331.02
            ),
            History(
                date,
                "ASF",
                4.43,
                "FSA",
                53.23
            ),
            History(
                date,
                "BNF",
                3.10,
                "HTR",
                2.89
            ),
            History(
                date,
                "HGE",
                234453.78,
                "XZC",
                202.09
            ),
            History(
                date,
                "NBV",
                2.23,
                "RUB",
                21.09
            ),
            History(
                date,
                "TRE",
                22.00,
                "MJU",
                12.42
            )
        )
        adapter.historyList = historyList

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_filter) {
            val action = HistoryFragmentDirections.actionHistoryFragmentToFilterFragment()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        // fun newInstance(param1: String, param2: String) = HistoryFragment()
    }
}