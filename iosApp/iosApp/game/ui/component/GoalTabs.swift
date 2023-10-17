//
//  SettingScreen2.swift
//  iosApp
//
//  Created by Hao Wu on 9/7/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct GoalTabs: View {
    
    @State private var selectedTab = 0
    @State private var value = 5000.0

    var body: some View {
        
        VStack(alignment: .center) {
            Tabs(
                tabs: .constant(["Daily Goal", "Weekly Goal"]),
                selection: $selectedTab,
                underlineColor: .black) { title, isSelected in
                    Text(title.uppercased())
//                       .font(.system(size: 20))
                       .fontWeight(.semibold)
                       .foregroundColor(isSelected ? .black : .gray)
                }

            Text("Selected tab is at index \(selectedTab)").padding(.top, 20)
            Text("Selected tab is at index \(value)").padding(.top, 20)
            Slider(value: $value, in: 5000...20000, step: 4)
            Spacer()
        }
        .padding(.top, 20)
        
    }
    
}

struct Tabs<Label: View>: View {
  @Binding var tabs: [String] // The tab titles
  @Binding var selection: Int // Currently selected tab
  let underlineColor: Color // Color of the underline of the selected tab
  // Tab label rendering closure - provides the current title and if it's the currently selected tab
  let label: (String, Bool) -> Label

  var body: some View {
    // Pack the tabs horizontally and allow them to be scrolled
      ScrollView(.horizontal, showsIndicators: false) {
          HStack(spacing: 45) {
              ForEach(tabs, id: \.self) {
                  self.tab(title: $0)
              }
          }
          .padding(.horizontal, 3) // Tuck the out-most elements in a bit
          .frame(width: UIScreen.main.bounds.width)
      }
  }

  private func tab(title: String) -> some View {
    let index = self.tabs.firstIndex(of: title)!
    let isSelected = index == selection
    return Button(action: {
      // Allows for animated transitions of the underline,
      // as well as other views on the same screen
      withAnimation {
         self.selection = index
      }
    }) {
      label(title, isSelected)
        .overlay(Rectangle() // The line under the tab
            .frame(height: 2)
        // The underline is visible only for the currently selected tab
        .foregroundColor(isSelected ? underlineColor : .clear)
        .padding(.top, 2)
        // Animates the tab selection
        .transition(.move(edge: .bottom)) ,alignment: .bottomLeading)
        .font(.WelcomeCaption)
    }
  }
}


struct SettingScreen2_Previews: PreviewProvider {
    static var previews: some View {
        GoalTabs()
    }
}
