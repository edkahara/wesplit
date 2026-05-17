package com.example.wesplit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.Currency
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidApp() {
    val locale = Locale.getDefault()
    println("locale $locale")
    val currency =  Currency.getInstance(locale)

    var checkAmount by remember { mutableDoubleStateOf(0.0) }
    var checkAmountString by remember { mutableStateOf("%.2f".format(checkAmount) )}
    var numberOfPeople by remember { mutableIntStateOf(2) }
    var numberOfPeopleString by remember { mutableStateOf(numberOfPeople.toString()) }
    var tipPercentage by remember { mutableIntStateOf(20) }
    var expanded by remember { mutableStateOf(false) }

    val tipPercentages = listOf(10, 15, 20, 25, 0)

    MaterialTheme {
        Column (
            modifier = Modifier
                .background(Color.White)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = checkAmountString,
                    onValueChange = { newCheckAmount ->
                        // Ensure there is only 1 decimal point
                        val count = newCheckAmount.count { it == '.' }
                        if (count != 1) return@TextField
                        // Remove non-numbers and non-decimals
                        val filteredText = newCheckAmount.replace("[^0-9.]".toRegex(), "")
                        // Split new amount into parts, then join it back, taking only 2 numbers after the decimals
                        val parts = filteredText.split('.')
                        val newAmount = parts[0] + '.' + parts[1].take(2) // Take only 2 decimal places
                        val newDouble = newAmount.toDoubleOrNull() ?: 0.0
                        checkAmountString = "%.2f".format(newDouble)
                        checkAmount = checkAmountString.toDouble()
                    },
                    label = { Text("Amount") },
                    prefix = { Text(currency.getSymbol(locale)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Number of people")

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = numberOfPeopleString,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        label = { Text("Number of people") },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        (2 until 100).forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        option.toString(),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    numberOfPeopleString = option.toString()
                                    numberOfPeople = option
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text("How much do you want to tip?")
                SingleChoiceSegmentedButtonRow() {
                    tipPercentages.forEachIndexed { index, option ->
                        SegmentedButton (
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = tipPercentages.size
                            ),
                            onClick = { tipPercentage = option },
                            selected = tipPercentage == option,
                            label = { Text("$option%") },
                            icon = {}
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = checkAmountString,
                    onValueChange = {},
                    readOnly = true,
                    prefix = { Text(currency.getSymbol(locale)) },
                )
            }
        }
2
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AndroidApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AndroidApp()
}